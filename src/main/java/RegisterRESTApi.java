import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.HTTP;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/auth/registration")
public class RegisterRESTApi extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        BufferedReader reader = req.getReader();
        StringBuffer sb = new StringBuffer();
        while (reader.ready()) {
            sb.append(reader.readLine());
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readValue(sb.toString(), JsonNode.class);
        String email = node.get("email").asText();
        String password = node.get("password").asText();
        resp.setStatus(201);
        String jsonString = String.format("{\"email\": \"%s\", \"password\": %s}", email, password);
        writer.print(jsonString);
    }
}
