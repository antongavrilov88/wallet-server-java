package controller;

import exceptions.DAOException;
import exceptions.EmailNotFoundException;
import exceptions.WrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import service.dto.RequestDto;
import service.dto.ResponseDto;
import service.UserDAOServiceLogin;

@Component
public class LoginRESTApi extends RESTApi {

    UserDAOServiceLogin userDAOService;

    public LoginRESTApi(UserDAOServiceLogin userDAOService) {
        this.userDAOService = userDAOService;
    }


    public ResponseEntity<?> login(RequestDto user) {
        try {
            return userDAOService.login(user);
        } catch (DAOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Internal server error\"}");
        } catch (EmailNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Email not found\"}");
        } catch (WrongPasswordException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Wrong password\"}");
        }
    }
}
