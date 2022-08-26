package controller;

import exceptions.DAOException;
import exceptions.EmailNotFoundException;
import exceptions.NotValidEmailException;
import exceptions.WrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import service.dto.RequestDto;
import service.dto.ResponseDto;
import service.UserDAOServiceLogin;

import java.io.IOException;

@Component
public class LoginRESTApi extends RESTApi {

    UserDAOServiceLogin userDAOService;

    public LoginRESTApi(UserDAOServiceLogin userDAOService) {
        this.userDAOService = userDAOService;
    }


    public ResponseEntity<?> login(RequestDto user) {
         try {
             return userDAOService.login(user);
        } catch (WrongPasswordException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Wrong password\"}");
        } catch (NotValidEmailException e) {
             throw new RuntimeException(e);
         }
    }
}
