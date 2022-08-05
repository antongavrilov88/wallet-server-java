package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import exceptions.DAOException;
import exceptions.EmailConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserDAOServiceRegistr;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class RegistrRESTApi extends MyHttpServlet {
    private static UserDAOServiceRegistr userDAOService = new UserDAOServiceRegistr();

    public static String register(String email, String password) throws IOException {
        try {
            return userDAOService.register(email, password);
        } catch (DAOException e) {
            return "{\"message\": \"Internal server error\"}";
        } catch (EmailConflictException e) {
            return "{\"message\": \"Email is already registered!\"}";
        }
    }
}
