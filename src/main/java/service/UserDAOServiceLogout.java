package service;

import exceptions.NotAuthorizedException;
import model.DAO.TokenDAO;
import model.DAO.UserDAO;
import model.Token;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDAOServiceLogout extends UserDAOService {

    private TokenDAO tokenDAO;
    public UserDAOServiceLogout(UserDAO userDAO, TokenDAO tokenDAO, UserModelAssembler assembler, BCryptPasswordEncoder passwordEncoder) {
        super(userDAO, tokenDAO, assembler, passwordEncoder);
        this.tokenDAO = tokenDAO;
    }



    public void logout(String accessToken) throws NotAuthorizedException {
        Token token = tokenDAO.findTokenByUserToken(accessToken);
        token.setIsActive(false);
        tokenDAO.update(token);
    }
}
