package service;

import model.DAO.UserDAOLogout;
import exceptions.NotAuthorizedEception;
import model.Token;

public class UserDAOServiceLogout extends UserDAOService {

    private UserDAOLogout userDAOLogout = new UserDAOLogout();
    public UserDAOServiceLogout() {

    }



    public void logout(String userToken) throws NotAuthorizedEception {
        Token token = userDAOLogout.getToken(userToken);
        if (token != null && token.isActive()) {
            token.setActive(false);
            userDAOLogout.update(token);
        } else {
            throw new NotAuthorizedEception();
        }
    }
}
