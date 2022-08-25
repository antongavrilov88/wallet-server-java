package service;

import exceptions.DAOException;
import exceptions.EmailConflictException;
import exceptions.NotValidEmailException;
import model.DAO.TokenDAO;
import model.DAO.UserDAO;
import model.Token;
import model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import service.dto.RequestDto;
import service.dto.ResponseDto;
import utils.RequestType;

//TODO reconsider classes structure, only one of the UserDAOService may live
@Component
@Primary
public class UserDAOServiceRegistr extends UserDAOService {



    public UserDAOServiceRegistr(UserDAO userDAO, TokenDAO tokenDAO, UserModelAssembler assembler, BCryptPasswordEncoder passwordEncoder) {
        super(userDAO, tokenDAO, assembler, passwordEncoder);
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
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        if ((savedUser = userDAO.save(user)) != null) {
            //TODO refactor token creation
            int hashCodeToken = user.getEmail().hashCode() + user.getPassword().hashCode();
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
