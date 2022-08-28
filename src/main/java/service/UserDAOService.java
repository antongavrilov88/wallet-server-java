package service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.*;
import model.DAO.TokenDAO;
import model.DAO.UserDAO;
import model.Token;
import model.User;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import service.dto.RequestDto;
import service.dto.ResponseDto;
import utils.RequestType;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
public abstract class UserDAOService implements UserDetailsService {

    final UserDAO userDAO;
    final TokenDAO tokenDAO;
    final UserModelAssembler assembler;

    final BCryptPasswordEncoder passwordEncoder;
    protected UserDAOService(UserDAO userDAO, TokenDAO tokenDAO, UserModelAssembler assembler, BCryptPasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.tokenDAO = tokenDAO;
        this.assembler = assembler;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDAO.findByEmail(s);
        //TODO implement roles into API
        Collection<? extends GrantedAuthority> authorities = Collections.emptyList();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    public CollectionModel<EntityModel<User>> findAllUsers() {
        List<EntityModel<User>> users = userDAO.findAll().stream()
                .map(assembler :: toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(users,
                linkTo(methodOn(UserDAO.class).findAll()).withRel("users"));
    }

    void checkUniqueEmail(String email) throws EmailConflictException {
        List<User> allUsers = userDAO.findAll();
        for (User user : allUsers) {
            if (user.getEmail().equals(email)) {
                throw new EmailConflictException();
            }
        }
    }

    void validateEmail(String email) throws NotValidEmailException {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new NotValidEmailException();
        }
    }

    void validatePassword(User user) throws WrongPasswordException {
        if (!passwordEncoder.matches(user.getPassword(), userDAO.findByEmail(user.getEmail()).getPassword())) {
            System.out.println(passwordEncoder.encode(user.getPassword()));
            System.out.println(userDAO.findByEmail(user.getEmail()).getPassword());
            throw new WrongPasswordException();
        }
    }

    public User findUserById(int id) {
        return userDAO.findById(id);
    }



}
