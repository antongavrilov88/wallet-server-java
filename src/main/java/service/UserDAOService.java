package service;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import model.repository.UserRepository;

import java.util.List;


public abstract class UserDAOService {

    private UserRepository userRepository;
    protected static final String jsonReturnString = "{ \"type\": \"%s\", \"data\": { \"id\": %d, \"attributes\": { \"email\": \"%s\", \"token\": \"%s\", \"is_admin\": %s}}}";

    protected UserDAOService() {
    }

    //TODO all methods in UserDAOService
    public User findUserByEmail(String email) {
        //return userRepository.findByEmail(email);
        return null;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        // return userRepository.findById(id);
        return null;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }


}
