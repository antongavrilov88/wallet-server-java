package model.DAO;

import model.User;
import model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Component
public class UserDAO {

    private final UserRepository repository;

    public UserDAO(UserRepository repository) {
        this.repository = repository;
    }


    public User findByEmail(String email) {
        for (User user : repository.findAll()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }


    public User findById(long id) {
        return repository.findById(id).get();
    }


    public User save(User user) {
        try {
            return repository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public void delete(User user) {
        repository.delete(user);
    }


    public void update(User user) {
        //TODO update
    }

    public List<User> findAll() {
        return repository.findAll();
    }
}
