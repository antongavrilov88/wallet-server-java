package RESTApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.DAOException;
import exceptions.EmailConflictException;
import exceptions.NotAuthorizedEception;
import service.UserDAOService;
import service.UserDAOServiceLogout;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/auth/logout")
public class LogoutRESTApi extends MyHttpServlet {
    UserDAOServiceLogout userDAOService = new UserDAOServiceLogout();

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        try {
            userDAOService.logout(getToken(req));
            resp.setStatus(200);
            writer.write("");
        } catch (NotAuthorizedEception e) {
            resp.setStatus(401);
            writer.write("");
        }
    }
}
