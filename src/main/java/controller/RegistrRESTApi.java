package controller;

import exceptions.*;
import model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import service.UserDAOServiceLogin;
import service.dto.RequestDto;
import service.UserDAOServiceRegistr;

import java.io.IOException;

@Component
public class RegistrRESTApi extends RESTApi {
    private final UserDAOServiceRegistr userDAOServiceRegistr;
    private final UserDAOServiceLogin userDAOServiceLogin;

    public RegistrRESTApi(UserDAOServiceRegistr userDAOService,
                          UserDAOServiceLogin userDAOServiceLogin) {
        this.userDAOServiceRegistr = userDAOService;
        this.userDAOServiceLogin = userDAOServiceLogin;
    }


    public ResponseEntity<?> register(RequestDto user) throws IOException {
        try {
            return userDAOServiceRegistr.register(user);
        } catch (DAOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Internal server error\"}");
        } catch (EmailConflictException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Email is already registered!\"}");
        } catch (NotValidEmailException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Not a valid email!\"}");
        }
    }

    public User findById(int id) {
        return userDAOServiceRegistr.findUserById(id);
    }
}
