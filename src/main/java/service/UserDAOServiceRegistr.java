package service;

import exceptions.DAOException;
import exceptions.EmailConflictException;
import exceptions.NotValidEmailException;
import model.DAO.TokenDAO;
import model.DAO.UserDAO;
import model.Token;
import model.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDAOServiceRegistr extends UserDAOService {



    public UserDAOServiceRegistr(UserDAO userDAO, TokenDAO tokenDAO, UserModelAssembler assembler) {
        super(userDAO, tokenDAO, assembler);
    }

    public ResponseEntity<?> register(RequestResponseDto rrDtoReq) throws EmailConflictException, DAOException, NotValidEmailException {
        RequestResponseDto.UserDto userDto = rrDtoReq.getData();
        User user = userDto.getUser().getContent();
        String type = rrDtoReq.getType();
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
        RequestResponseDto rrDtoResp = new RequestResponseDto();
        RequestResponseDto.UserDto userDtoResp = rrDtoResp.new UserDto(entityModel, token);
        rrDtoReq.setData(userDtoResp);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(rrDtoResp);
    }


}
