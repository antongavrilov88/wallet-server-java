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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
@Component
public class UserDAOServiceLogin extends UserDAOService{
        public UserDAOServiceLogin(UserDAO userDAO, TokenDAO tokenDAO, UserModelAssembler assembler) {
        super(userDAO, tokenDAO, assembler);
    }





    public ResponseEntity<?> login(RequestResponseDto rrDtoReq) throws EmailNotFoundException, DAOException, WrongPasswordException {
        User user = rrDtoReq.getData().getUser().getContent();
        try {
            checkUniqueEmail(user.getEmail());
        } catch (EmailConflictException e) {
            validatePassword(user);
            User userFound = userDAO.findByEmail(user.getEmail());
            int hashCodeToken = userFound.getEmail().hashCode() + userFound.getPass().hashCode();
            int usersId = userFound.getId();
            Token token = new Token(String.valueOf(hashCodeToken), usersId, true);
            if (tokenDAO.save(token) == null) {
                throw new DAOException();
            }
            EntityModel<User> entityModel = assembler.toModel(userFound);
            RequestResponseDto rrDtoResp = new RequestResponseDto();
            RequestResponseDto.UserDto userDtoResp = rrDtoResp.new UserDto(entityModel, token);
            rrDtoReq.setData(userDtoResp);
            return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(rrDtoResp);
        }
        throw new EmailNotFoundException();
    }
}
