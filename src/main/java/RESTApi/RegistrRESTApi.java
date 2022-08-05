package RESTApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.DAOException;
import exceptions.EmailConflictException;
import service.UserDAOServiceRegistr;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/auth/registration")
public class RegistrRESTApi extends MyHttpServlet {
    UserDAOServiceRegistr userDAOService = new UserDAOServiceRegistr();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();
        JsonNode node = null;
        try {
            node = getJson(req);
        } catch (JsonProcessingException e) {
            resp.setStatus(400);
            writer.write("Wrong API, not a JSON");
        }
        String email = node.get("data").get("attributes").get("email").asText();
        String password = node.get("data").get("attributes").get("password").asText();
        try {
            resp.setStatus(201);
            writer.write(userDAOService.register(email, password));
        } catch (DAOException e) {
            resp.setStatus(500);
            writer.write("{\"message\": \"Internal server error\"}");
        } catch (EmailConflictException e) {
            resp.setStatus(409);
            writer.write("{\"message\": \"Email is already registered!\"}");
        }
    }
}
