package service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
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

import java.util.Date;

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
        Token accessTokenToken;
        Token refreshTokenToken;
        User savedUser;
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        if ((savedUser = userDAO.save(user)) != null) {
            Algorithm algorithm = Algorithm.HMAC256("dickcheese".getBytes());
            String accessToken = JWT.create()
                    .withSubject(savedUser.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1440*60*100000)) // 24h
                    .withIssuer(reqDto.getIssuer())
                    .sign(algorithm);

            String refreshToken = JWT.create()
                    .withSubject(savedUser.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 20160*60*100000)) // 2 weeks
                    .withIssuer(reqDto.getIssuer())
                    .sign(algorithm);

            int userId = savedUser.getId();
            accessTokenToken = new Token("Bearer " + accessToken, userId, true);
            refreshTokenToken = new Token("Bearer " + refreshToken, userId, true);
            if (tokenDAO.save(accessTokenToken) == null || tokenDAO.save(refreshTokenToken) == null) {
                userDAO.delete(user);
                tokenDAO.delete(accessTokenToken);
                tokenDAO.delete(refreshTokenToken);
                throw new DAOException();
            }
        } else {
            throw new DAOException();
        }
        EntityModel<User> entityModel = assembler.toModel(savedUser);
        ResponseDto respDto = new ResponseDto();
        ResponseDto.UserDto userDtoResp = respDto.new UserDto();
        userDtoResp.transform(entityModel, accessTokenToken, refreshTokenToken);
        respDto.setData(userDtoResp);
        respDto.setType(type);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(respDto);
    }


}
