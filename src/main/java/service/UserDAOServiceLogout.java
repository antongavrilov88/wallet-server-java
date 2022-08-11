package service;

import exceptions.NotAuthorizedEception;
import model.DAO.TokenDAO;
import model.DAO.UserDAO;

public class UserDAOServiceLogout extends UserDAOService {

    private TokenDAO TokenDAO;
    public UserDAOServiceLogout(UserDAO userDAO, TokenDAO tokenDAO, UserModelAssembler assembler) {
        super(userDAO, tokenDAO, assembler);
    }



    public void logout(String userToken) throws NotAuthorizedEception {
        /* Token token = TokenDAO.getToken(userToken);
        if (token != null && token.isActive()) {
            token.setActive(false);
            TokenDAO.update(token);
        } else {
            throw new NotAuthorizedEception();
        } */
    }
}
