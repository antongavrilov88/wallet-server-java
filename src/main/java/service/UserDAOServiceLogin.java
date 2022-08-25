package service;

import exceptions.DAOException;
import exceptions.EmailConflictException;
import exceptions.EmailNotFoundException;
import exceptions.WrongPasswordException;
import model.DAO.TokenDAO;
import model.DAO.UserDAO;
import model.Token;
import model.User;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
@Component
public class UserDAOServiceLogin extends UserDAOService{
        public UserDAOServiceLogin(UserDAO userDAO, TokenDAO tokenDAO, UserModelAssembler assembler, BCryptPasswordEncoder passwordEncoder) {
        super(userDAO, tokenDAO, assembler, passwordEncoder);
    }





    public ResponseEntity<?> login(RequestDto reqDto) throws EmailNotFoundException, DAOException, WrongPasswordException {
        User user = reqDto.getData();
        String type = reqDto.getType();
        if (!type.equals(RequestType.AUTH.toString())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Wrong request type\"}");
        }
        try {
            checkUniqueEmail(user.getEmail());
        } catch (EmailConflictException e) {
            validatePassword(user);
            User userFound = userDAO.findByEmail(user.getEmail());
            int hashCodeToken = userFound.getEmail().hashCode() + userFound.getPassword().hashCode();
            int usersId = userFound.getId();
            Token token = new Token(String.valueOf(hashCodeToken), usersId, true);
            if (tokenDAO.save(token) == null) {
                throw new DAOException();
            }
            EntityModel<User> entityModel = assembler.toModel(userFound);
            ResponseDto respDto = new ResponseDto();
            ResponseDto.UserDto userDtoResp = respDto.new UserDto();
            userDtoResp.transform(entityModel, token);
            respDto.setData(userDtoResp);
            respDto.setType(type);
            return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(respDto);
        }
        throw new EmailNotFoundException();
    }
}
