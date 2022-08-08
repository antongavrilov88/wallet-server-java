package controller;

import exceptions.DAOException;
import exceptions.EmailConflictException;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            return new ResponseEntity("{\"message\": \"Internal server error\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (EmailConflictException e) {
            return  new ResponseEntity("{\"message\": \"Email is already registered!\"}", HttpStatus.CONFLICT);
        }
    }
}
