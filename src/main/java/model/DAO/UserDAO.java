package model.DAO;

import model.User;
import model.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAO {

    private final UserRepository repository;

    public UserDAO(UserRepository repository) {
        this.repository = repository;
    }


    public User findByEmail(String email) {
        for (User user : repository.findAll()) {
            if (user.getUsername().equals(email)) {
                return user;
            }
        }
        return null;
    }


    public User findById(int id) {
        //TODO resolve id type issue
        return repository.findById((long) id).get();
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
