package controller;

import exceptions.NotAuthorizedException;
import model.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.UserDAOServiceLogout;

import java.io.IOException;

@Component
public class LogoutRESTApi extends RESTApi {
    UserDAOServiceLogout userDAOService;

    @DeleteMapping("auth/logout")
    public ResponseEntity<?> logout(@RequestParam Token token) throws IOException {
        try {
            userDAOService.logout(token);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON).body(null);
        } catch (NotAuthorizedException e) {
            ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.APPLICATION_JSON).body(null);
        }
        return null;
    }
}
