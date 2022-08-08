package service;

import exceptions.NotAuthorizedEception;
import model.DAO.TokenDAO;
import model.DAO.UserDAO;
import model.Token;

public class UserDAOServiceLogout extends UserDAOService {

    private TokenDAO TokenDAO;
    public UserDAOServiceLogout() {

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
