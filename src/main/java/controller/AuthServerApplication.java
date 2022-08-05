package controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AuthServerApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AuthServerApplication.class);
        Map<String, Object> customConfig = new HashMap<>();
        customConfig.put("server.port", "3003");
        customConfig.put("host", "192.168.31.164");

        app.setDefaultProperties(customConfig);
        app.run(args);
    }
}
