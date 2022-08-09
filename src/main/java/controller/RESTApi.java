package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import service.UserDAOService;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class RESTApi {
    UserDAOService userDAOService;
    public String getToken(HttpServletRequest req) {
        return req.getHeader("Authorization");
    }

    public JsonNode getJson(HttpServletRequest req) throws IOException, JsonProcessingException {
        BufferedReader reader = req.getReader();
        StringBuffer sb = new StringBuffer();
        while (reader.ready()) {
            sb.append(reader.readLine());
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readValue(sb.toString(), JsonNode.class);
        return node;
    }

    public CollectionModel<EntityModel<User>> findAll() {
        return userDAOService.findAllUsers();
    }
}
