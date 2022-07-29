package service;

import DAO.UserDAOLogin;
import exceptions.DAOException;
import exceptions.EmailConflictException;
import exceptions.EmailNotFoundException;
import exceptions.WrongPasswordException;
import model.Token;
import model.User;
import utils.RequestType;

public class UserDAOServiceLogin extends UserDAOService{
    private UserDAOLogin userDAOLogin = new UserDAOLogin();
    public UserDAOServiceLogin() {

    }

    @Override
    void validateEmail(String email) throws EmailNotFoundException {
        if (userDAOLogin.findByEmail(email) == null) {
            throw new EmailNotFoundException();
        }
    }

    void validatePassword(String email, String password) throws WrongPasswordException {
        if (!password.equals(userDAOLogin.findByEmail(email).getPass())) {
            throw new WrongPasswordException();
        }
    }

    public String login(String email, String password) throws EmailNotFoundException, DAOException, WrongPasswordException{
        validateEmail(email);
        validatePassword(email, password);
        User user = userDAOLogin.findByEmail(email);
        int hashCodeToken = email.hashCode() + password.hashCode();
        int usersId = user.getId();
        Token token = new Token(String.valueOf(hashCodeToken), usersId, true);
        if (userDAOLogin.save(token) == null) {
            throw new DAOException();
        }
        String jsonString = String.format(UserDAOService.jsonReturnString, RequestType.AUTH, user.getId(), email, token.getUserToken(), user.isAdmin());
        return jsonString;
    }
}
