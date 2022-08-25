package service;

import exceptions.EmailConflictException;
import exceptions.NotValidEmailException;
import exceptions.WrongPasswordException;
import model.DAO.TokenDAO;
import model.DAO.UserDAO;
import model.User;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
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
        if (!user.getPassword().equals(userDAO.findByEmail(user.getEmail()).getPassword())) {
            System.out.println(user.getPassword().hashCode());
            System.out.println(userDAO.findByEmail(user.getEmail()).getPassword());
            throw new WrongPasswordException();
        }
    }

    public User findUserById(int id) {
        return userDAO.findById(id);
    }

}
