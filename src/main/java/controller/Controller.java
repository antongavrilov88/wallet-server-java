package controller;

import model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.UserDAOServiceRegistr;

import java.io.IOException;

@RestController
public class Controller {

    UserDAOServiceRegistr userDAOService = new UserDAOServiceRegistr();

    @PostMapping("/auth/registration")
    public String register(@RequestBody String email, @RequestBody String password) throws IOException {
        return RegistrRESTApi.register(email, password);
    }

    @GetMapping("/")
    public String index() throws IOException {
        return "Бля, работает";
    }
    /*
    @PostMapping("/auth/login")
    public void login() {
        LoginRESTApi.login();
    }

    @PostMapping("/auth/logout")
    public void logout() {
        LogoutRESTApi.logout();
    }

    @GetMapping("/auth/users")
    public void users() {

    }
    */
}
