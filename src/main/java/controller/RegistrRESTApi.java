package controller;

import exceptions.DAOException;
import exceptions.EmailConflictException;
import exceptions.NotValidEmailException;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import service.UserDAOServiceRegistr;

import java.io.IOException;

@Component
public class RegistrRESTApi extends RESTApi {
    private final UserDAOServiceRegistr userDAOService;

    public RegistrRESTApi(UserDAOServiceRegistr userDAOService) {
        this.userDAOService = userDAOService;

    }

    public ResponseEntity<?> register(User user) throws IOException {
        try {
            return userDAOService.register(user);
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
}
