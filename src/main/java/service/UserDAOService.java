package service;

import model.DAO.TokenDAO;
import model.DAO.UserDAO;
import model.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import service.dto.UserEntitylAssembler;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserDAOService implements UserDetailsService, EntityServiceInterface<User> {

    final UserDAO userDAO;
    final TokenDAO tokenDAO;
    final UserEntitylAssembler assembler;


    protected UserDAOService(UserDAO userDAO, TokenDAO tokenDAO, UserEntitylAssembler assembler) {
        this.userDAO = userDAO;
        this.tokenDAO = tokenDAO;
        this.assembler = assembler;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDAO.findByEmail(s);
        //TODO implement roles into API
        Collection<? extends GrantedAuthority> authorities = Collections.emptyList();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    public CollectionModel<EntityModel<User>> findAllUsers() {
        /* List<EntityModel<User>> users = userDAO.findAll().stream()
                .map(assembler :: toModel)
                .collect(Collectors.toList()); */
        return null;
        // CollectionModel.of(users,
               // linkTo(methodOn(UserDAO.class).findAll()).withRel("users"));
    }



    public User findUserById(int id) {
        return userDAO.findById(id);
    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User create(User entity) {
        return null;
    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public User update(User entity) {
        return null;
    }
}
