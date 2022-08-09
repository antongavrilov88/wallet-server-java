package service;

import exceptions.DAOException;
import exceptions.EmailNotFoundException;
import exceptions.WrongPasswordException;
import model.DAO.TokenDAO;
import model.DAO.UserDAO;

public class UserDAOServiceLogin extends UserDAOService{
        public UserDAOServiceLogin(UserDAO userDAO, TokenDAO tokenDAO, UserModelAssembler assembler) {
        super(userDAO, tokenDAO, assembler);
    }


    void validateEmail(String email) throws EmailNotFoundException {
        if (userDAO.findByEmail(email) == null) {
            throw new EmailNotFoundException();
        }
    }

    void validatePassword(String email, String password) throws WrongPasswordException {
        if (!password.equals(userDAO.findByEmail(email).getPass())) {
            throw new WrongPasswordException();
        }
    }

    public String login(String email, String password) throws EmailNotFoundException, DAOException, WrongPasswordException{
        /*checkUniqueEmail(email);
        validatePassword(email, password);
        User user = userDAO.findByEmail(email);
        int hashCodeToken = email.hashCode() + password.hashCode();
        int usersId = user.getId();
        Token token = new Token(String.valueOf(hashCodeToken), usersId, true);
        if (userDAO.save(token) == null) {
            throw new DAOException();
        }
        String jsonString = String.format(UserDAOService.jsonReturnString, RequestType.AUTH, user.getId(), email, token.getUserToken(), user.isAdmin());
        return jsonString; */ return null;
    }
}
