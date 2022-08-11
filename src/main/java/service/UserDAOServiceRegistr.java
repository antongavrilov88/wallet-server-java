package service;

import exceptions.DAOException;
import exceptions.EmailConflictException;
import exceptions.NotValidEmailException;
import model.DAO.TokenDAO;
import model.DAO.UserDAO;
import model.Token;
import model.User;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAOServiceRegistr extends UserDAOService {



    public UserDAOServiceRegistr(UserDAO userDAO, TokenDAO tokenDAO, UserModelAssembler assembler) {
        super(userDAO, tokenDAO, assembler);
    }

    public ResponseEntity<?> register(User user) throws EmailConflictException, DAOException, NotValidEmailException {
        validateEmail(user.getEmail());
        checkUniqueEmail(user.getEmail());
        Token token;
        User savedUser;
        if ((savedUser = userDAO.save(user)) != null) {
            int hashCodeToken = user.getEmail().hashCode() + user.getPass().hashCode();
            int userId = savedUser.getId();
            token = new Token(String.valueOf(hashCodeToken), userId, true);
            if (tokenDAO.save(token) == null) {
                userDAO.delete(user);
                throw new DAOException();
            }
        } else {
            throw new DAOException();
        }
        EntityModel<User> entityModel = assembler.toModel(savedUser);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    void checkUniqueEmail(String email) throws EmailConflictException {
        List<User> allUsers = userDAO.findAll();
        for (User user : allUsers) {
            if (user.getEmail().equals(email)) {
                throw new EmailConflictException();
            }
        }
    }

    void validateEmail(String email) throws NotValidEmailException {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new NotValidEmailException();
        }
    }
}
