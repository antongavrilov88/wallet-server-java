package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class MyHttpServlet {

    public String getToken(HttpServletRequest req) {
        return req.getHeader("Authorization");
    }

    public static JsonNode getJson(HttpServletRequest req) throws IOException, JsonProcessingException {
        BufferedReader reader = req.getReader();
        StringBuffer sb = new StringBuffer();
        while (reader.ready()) {
            sb.append(reader.readLine());
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readValue(sb.toString(), JsonNode.class);
        return node;
    }
}
