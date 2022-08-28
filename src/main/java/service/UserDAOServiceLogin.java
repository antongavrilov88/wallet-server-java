package service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import exceptions.*;
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

import java.util.Date;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
@Component
public class UserDAOServiceLogin extends UserDAOService{
        public UserDAOServiceLogin(UserDAO userDAO, TokenDAO tokenDAO, UserModelAssembler assembler, BCryptPasswordEncoder passwordEncoder) {
        super(userDAO, tokenDAO, assembler, passwordEncoder);
    }




    public ResponseEntity<?> login(RequestDto requestDto) throws WrongPasswordException, NotValidEmailException {
        validateEmail(requestDto.getData().getEmail());
        validatePassword(requestDto.getData());
        User user = requestDto.getData();
        String type = requestDto.getType();
        String issuer = requestDto.getIssuer();
        if (!type.equals(RequestType.AUTH.toString())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Wrong request type\"}");
        }
        User userFound = userDAO.findByEmail(user.getEmail());
        int usersId = userFound.getId();
        Algorithm algorithm = Algorithm.HMAC256("dickcheese".getBytes());
        String accessToken = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1440*60*100000)) //24h token
                .withIssuer(issuer)
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 20160*60*100000)) // 2 weeks token
                .withIssuer(issuer)
                .sign(algorithm);

        Token accessTokenToken = new Token("Bearer " + accessToken, usersId, true);
        Token refreshTokenToken = new Token("Bearer " + refreshToken, usersId, true);
        if (tokenDAO.save(accessTokenToken) == null || tokenDAO.save(refreshTokenToken) == null) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Internal server error\"}");
        }
        EntityModel<User> entityModel = assembler.toModel(userFound);
        ResponseDto respDto = new ResponseDto();
        ResponseDto.UserDto userDtoResp = respDto.new UserDto();
        userDtoResp.transform(entityModel, accessTokenToken, refreshTokenToken);
        respDto.setData(userDtoResp);
        respDto.setType(type);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(respDto);
    }
}
