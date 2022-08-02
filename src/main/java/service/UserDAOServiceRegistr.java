package service;

import DAO.UserDAORegistr;
import exceptions.DAOException;
import exceptions.EmailConflictException;
import model.Token;
import model.User;
import utils.RequestType;

public class UserDAOServiceRegistr extends UserDAOService {
    private UserDAORegistr userDAORegistr;

    public UserDAOServiceRegistr() {
        this.userDAORegistr = new UserDAORegistr();
    }

    public String register(String email, String password) throws EmailConflictException, DAOException {
        validateEmail(email);
        User user = new User(email, password);
        Token token;
        if (userDAORegistr.save(user) != null) {
            int hashCodeToken = email.hashCode() + password.hashCode();
            int usersId = user.getId();
            token = new Token(String.valueOf(hashCodeToken), usersId, true);
            if (userDAORegistr.save(token) == null) {
                userDAORegistr.delete(user);
                throw new DAOException();
            }
        } else {
            throw new DAOException();
        }
        String jsonString = String.format(UserDAOService.jsonReturnString, RequestType.AUTH, user.getId(), email, token.getUserToken(), user.isAdmin());
        return jsonString;
    }

    @Override
    void validateEmail(String email) throws EmailConflictException {
        if (findUserByEmail(email) != null) {
            throw new EmailConflictException();
        }
    }
}
