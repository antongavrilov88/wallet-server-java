package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import exceptions.DAOException;
import exceptions.EmailConflictException;
import exceptions.EmailNotFoundException;
import exceptions.WrongPasswordException;
import model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import service.UserDAOService;
import service.UserDAOServiceLogin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class LoginRESTApi extends RESTApi {

    UserDAOServiceLogin userDAOService;

    public LoginRESTApi(UserDAOServiceLogin userDAOService) {
        this.userDAOService = userDAOService;
    }


    public ResponseEntity<?> login(User user) {
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
