package service;

import exceptions.DAOException;
import exceptions.EmailConflictException;
import model.DAO.TokenDAO;
import model.DAO.UserDAO;
import model.Token;
import model.User;
import model.repository.TokenRepository;
import model.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAOServiceRegistr {
    private final UserDAO userDAO;
    private final TokenDAO tokenDAO;

    private final UserModelAssembler assembler;

    public UserDAOServiceRegistr(UserDAO userDAO, TokenDAO tokenDAO) {
        this.assembler = new UserModelAssembler();
        this.userDAO = userDAO;
        this.tokenDAO = tokenDAO;
    }

    public ResponseEntity<?> register(User user) throws EmailConflictException, DAOException {
        validateEmail(user.getEmail());
        Token token;
        User savedUser;
        if ((savedUser = userDAO.save(user)) != null) {
            int hashCodeToken = user.getEmail().hashCode() + user.getPass().hashCode();
            int userId = savedUser.getId();
            token = new Token(String.valueOf(hashCodeToken), userId, true);
            if (tokenDAO.save(token) == null) {
                userDAO.delete(user);
                throw new DAOException();
            }
        } else {
            throw new DAOException();
        }
        return null;
    }

    void validateEmail(String email) throws EmailConflictException {
        List<User> allUsers = userDAO.findAll();
        for (User user : allUsers) {
            if (user.getEmail().equals(email)) {
                throw new EmailConflictException();
            }
        }
    }
}
