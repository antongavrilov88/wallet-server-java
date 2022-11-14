package service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import exceptions.*;
import model.DAO.TokenDAO;
import model.DAO.UserDAO;
import model.Token;
import model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import service.dto.EntityDto;
import service.dto.RequestDto;
import service.dto.UserEntitylAssembler;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Service
public class AuthService implements AuthServiceInterface, UserDetailsService {

    final UserDAO userDAO;
    final TokenDAO tokenDAO;
    final UserEntitylAssembler assembler;

    public AuthService(UserDAO userDAO, TokenDAO tokenDAO, UserEntitylAssembler assembler) {
        this.userDAO = userDAO;
        this.tokenDAO = tokenDAO;
        this.assembler = assembler;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDAO.findByEmail(s);
        //TODO implement roles into API
        Collection<? extends GrantedAuthority> authorities = Collections.emptyList();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public EntityDto<User> register(RequestDto<User> requestDto) throws WrongRequestType, NotValidEmailException, EmailConflictException, DAOException {
        User user = requestDto.getData();
        userDAO.validateEmail(user.getEmail());
        userDAO.checkUniqueEmail(user.getEmail());
        Token accessTokenToken;
        Token refreshTokenToken;
        User savedUser;
        String password = userDAO.getPasswordEncoder().encode(user.getPassword());
        user.setPassword(password);
        if ((savedUser = userDAO.save(user)) != null) {
            Algorithm algorithm = Algorithm.HMAC256("dickcheese".getBytes());
            String accessToken = JWT.create()
                    .withSubject(savedUser.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1440*60*100000)) // 24h
                    .withIssuer(requestDto.getIssuer())
                    .sign(algorithm);

            String refreshToken = JWT.create()
                    .withSubject(savedUser.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 20160*60*100000)) // 2 weeks
                    .withIssuer(requestDto.getIssuer())
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
        EntityDto<User> entityDto = assembler.toModel(savedUser, accessTokenToken, refreshTokenToken);
        return entityDto;
    }

    @Override
    public EntityDto<User> login(RequestDto<User> requestDto) throws NotValidEmailException, DAOException, WrongPasswordException, UserNotFoundException {
        User user = requestDto.getData();
        userDAO.validateEmail(user.getEmail());
        userDAO.validatePassword(user);
        Token accessTokenToken;
        Token refreshTokenToken;
        User userFound;
        String password = userDAO.getPasswordEncoder().encode(user.getPassword());
        user.setPassword(password);
        if ((userFound = userDAO.findByEmail(user.getEmail())) != null) {
            Algorithm algorithm = Algorithm.HMAC256("dickcheese".getBytes());
            String accessToken = JWT.create()
                    .withSubject(user.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1440*60*100000)) // 24h
                    .withIssuer(requestDto.getIssuer())
                    .sign(algorithm);

            String refreshToken = JWT.create()
                    .withSubject(user.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 20160*60*100000)) // 2 weeks
                    .withIssuer(requestDto.getIssuer())
                    .sign(algorithm);

            int userId = userFound.getId();
            accessTokenToken = new Token("Bearer " + accessToken, userId, true);
            refreshTokenToken = new Token("Bearer " + refreshToken, userId, true);
            if (tokenDAO.save(accessTokenToken) == null || tokenDAO.save(refreshTokenToken) == null) {
                tokenDAO.delete(accessTokenToken);
                tokenDAO.delete(refreshTokenToken);
                throw new DAOException();
            }
        } else {
            throw new UserNotFoundException();
        }
        EntityDto<User> entityDto = assembler.toModel(user, accessTokenToken, refreshTokenToken);
        return entityDto;
    }

    @Override
    public void logout(String accessToken) throws NotAuthorizedException {
        //TODO develop main logic of the method LOGOUT
    }
}
