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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import service.dto.RequestDto;
import service.dto.ResponseDto;
import utils.RequestType;

@Component
public class UserDAOServiceRegistr extends UserDAOService {



    public UserDAOServiceRegistr(UserDAO userDAO, TokenDAO tokenDAO, UserModelAssembler assembler) {
        super(userDAO, tokenDAO, assembler);
    }

    public ResponseEntity<?> register(RequestDto reqDto) throws EmailConflictException, DAOException, NotValidEmailException {
        User user = reqDto.getData();
        String type = reqDto.getType();
        if (!type.equals(RequestType.AUTH.toString())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Wrong request type\"}");
        }
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
        ResponseDto respDto = new ResponseDto();
        ResponseDto.UserDto userDtoResp = respDto.new UserDto();
        userDtoResp.transform(entityModel, token);
        respDto.setData(userDtoResp);
        respDto.setType(type);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(respDto);
    }


}
