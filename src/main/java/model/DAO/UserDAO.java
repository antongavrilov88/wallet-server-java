package model.DAO;

import exceptions.DAOException;
import exceptions.EmailConflictException;
import exceptions.NotValidEmailException;
import exceptions.WrongPasswordException;
import model.User;
import model.repository.UserRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAO {

    private final UserRepository repository;

    final BCryptPasswordEncoder passwordEncoder;

    public UserDAO(UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public BCryptPasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email);
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


    public void checkUniqueEmail(String email) throws EmailConflictException {
        List<User> allUsers = repository.findAll();
        for (User user : allUsers) {
            if (user.getEmail().equals(email)) {
                throw new EmailConflictException();
            }
        }
    }

    public void validateEmail(String email) throws NotValidEmailException {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new NotValidEmailException();
        }
    }

    public void validatePassword(User user) throws WrongPasswordException {
        if (!passwordEncoder.matches(user.getPassword(), findByEmail(user.getEmail()).getPassword())) {
            throw new WrongPasswordException();
        }
    }
}
