package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import exceptions.DAOException;
import exceptions.EmailNotFoundException;
import exceptions.WrongPasswordException;
import model.User;
import org.springframework.stereotype.Component;
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


    public void login(User user) throws ServletException, IOException {

        try {
            userDAOService.login(user);
        } catch (DAOException e) {
            resp.setStatus(500);
            writer.write("{\"message\": \"Internal server error\"}");
        } catch (EmailNotFoundException e) {
            resp.setStatus(409);
            writer.write("{\"message\": \"Email is not found!\"}");
        } catch (WrongPasswordException e) {
            resp.setStatus(401);
            writer.write("{\"message\": \"Wrong password\"}");
        }
    }
}
