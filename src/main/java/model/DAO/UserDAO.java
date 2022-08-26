package model.DAO;

import exceptions.DAOException;
import model.User;
import model.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }


    public User findById(int id) {
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


    public void update(User user) throws DAOException {
        if (user.getId() != 0) {
            repository.save(user);
        } else {
            throw new DAOException();
        }
    }

    public List<User> findAll() {
        return repository.findAll();
    }
}
