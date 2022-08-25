package service;

import exceptions.NotAuthorizedException;
import model.DAO.TokenDAO;
import model.DAO.UserDAO;
import model.Token;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserDAOServiceLogout extends UserDAOService {

    private TokenDAO tokenDAO;
    public UserDAOServiceLogout(UserDAO userDAO, TokenDAO tokenDAO, UserModelAssembler assembler, BCryptPasswordEncoder passwordEncoder) {
        super(userDAO, tokenDAO, assembler, passwordEncoder);
    }



    public void logout(Token token) throws NotAuthorizedException {
        token.setIsActive(false);
        tokenDAO.update(token);
    }
}
