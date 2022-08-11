package service;

import model.DAO.TokenDAO;
import model.DAO.UserDAO;
import model.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


public abstract class UserDAOService {

    final UserDAO userDAO;
    final TokenDAO tokenDAO;
    final UserModelAssembler assembler;
    protected UserDAOService(UserDAO userDAO, TokenDAO tokenDAO, UserModelAssembler assembler) {
        this.userDAO = userDAO;
        this.tokenDAO = tokenDAO;
        this.assembler = assembler;
    }

    //TODO all methods in UserDAOService
    public User findUserByEmail(String email) {
        //return userRepository.findByEmail(email);
        return null;
    }

    public CollectionModel<EntityModel<User>> findAllUsers() {
        List<EntityModel<User>> users = userDAO.findAll().stream()
                .map(assembler :: toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(users,
                linkTo(methodOn(UserDAO.class).findAll()).withRel("users"));
    }

    public User findUserById(Long id) {
        // return userRepository.findById(id);
        return null;
    }

    /* public void saveUser(User user) {
        userRepository.save(user);
    }

    // public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    } */


}
