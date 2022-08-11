package service;

import exceptions.DAOException;
import exceptions.EmailConflictException;
import exceptions.EmailNotFoundException;
import exceptions.WrongPasswordException;
import model.DAO.TokenDAO;
import model.DAO.UserDAO;
import model.User;

public class UserDAOServiceLogin extends UserDAOService{
        public UserDAOServiceLogin(UserDAO userDAO, TokenDAO tokenDAO, UserModelAssembler assembler) {
        super(userDAO, tokenDAO, assembler);
    }





    public String login(User user) throws EmailNotFoundException, DAOException, WrongPasswordException, EmailConflictException {
        checkUniqueEmail(user.getEmail());
        validatePassword(user);
        User user = userDAO.findByEmail(email);
        int hashCodeToken = email.hashCode() + password.hashCode();
        int usersId = user.getId();
        Token token = new Token(String.valueOf(hashCodeToken), usersId, true);
        if (userDAO.save(token) == null) {
            throw new DAOException();
        }
        String jsonString = String.format(UserDAOService.jsonReturnString, RequestType.AUTH, user.getId(), email, token.getUserToken(), user.getIsAdmin());
        return jsonString; */ return null;
    }
}
