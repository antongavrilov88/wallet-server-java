package model.DAO;

import model.User;
import model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public class UserDAO {

    private final UserRepository repository;

    public UserDAO(UserRepository repository) {
        this.repository = repository;
    }


    public User findByEmail(String email) {
        //TODO findByEmail
        return null;
    }


    public User findById(int id) {
        //TODO findById
        return null;
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
        //TODO delete
    }


    public void update(User user) {
        //TODO update
    }

    public List<User> getAll() {
        //TODO getAll
        return null;
    }
}
