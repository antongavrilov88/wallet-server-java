package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import exceptions.DAOException;
import exceptions.EmailNotFoundException;
import exceptions.WrongPasswordException;
import service.UserDAOServiceLogin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/auth/login")
public class LoginRESTApi extends RESTApi {

    UserDAOServiceLogin userDAOService = new UserDAOServiceLogin();


    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        JsonNode node = null;
        try {
            node = getJson(req);
        } catch (JsonProcessingException e) {
            resp.setStatus(400);
            writer.write("Wrong API, not a JSON");
        }
        String email = node.get("data").get(0).get("attributes").get(0).get("email").asText();
        String password = node.get("data").get(0).get("attributes").get(0).get("password").asText();
        try {
            resp.setStatus(201);
            writer.write(userDAOService.login(email, password));
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
