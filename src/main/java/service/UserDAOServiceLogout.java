package service;

import exceptions.NotAuthorizedException;
import model.DAO.TokenDAO;
import model.DAO.UserDAO;
import model.Token;

public class UserDAOServiceLogout extends UserDAOService {

    private TokenDAO tokenDAO;
    public UserDAOServiceLogout(UserDAO userDAO, TokenDAO tokenDAO, UserModelAssembler assembler) {
        super(userDAO, tokenDAO, assembler);
    }



    public void logout(Token token) throws NotAuthorizedException {
        token.setIsActive(false);
        tokenDAO.update(token);
    }
}
