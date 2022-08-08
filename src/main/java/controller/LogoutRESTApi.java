package controller;

import exceptions.NotAuthorizedEception;
import service.UserDAOServiceLogout;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/auth/logout")
public class LogoutRESTApi extends RESTApi {
    UserDAOServiceLogout userDAOService = new UserDAOServiceLogout();


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
