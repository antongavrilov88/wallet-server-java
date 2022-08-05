package service;

import DAO.UserDAO;
import exceptions.EmailConflictException;
import exceptions.EmailNotFoundException;
import model.Token;
import model.User;

public abstract class UserDAOService {
    private UserDAO userDAO;
    protected static final String jsonReturnString = "{ \"type\": \"%s\", \"data\": { \"id\": %d, \"attributes\": { \"email\": \"%s\", \"token\": \"%s\", \"is_admin\": %s}}}";

    public UserDAOService() {
        this.userDAO = new UserDAO();
    }

    public User findUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public User findUserById(int id) {
        return userDAO.findById(id);
    }

    public void saveUser(User user) {
        userDAO.save(user);
    }

    public void deleteUser(User user) {
        userDAO.delete(user);
    }

    public void updateUser(User user) {
        userDAO.update(user);
    }

    public void updateToken(Token token) {
        userDAO.update(token);
    }

    void validateEmail(String email) throws EmailConflictException, EmailNotFoundException {

    }
}
