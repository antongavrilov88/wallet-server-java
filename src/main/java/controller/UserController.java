package controller;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class UserController {
    final
    RegistrRESTApi registrRESTApi;

    public UserController(RegistrRESTApi registrRESTApi) {
        this.registrRESTApi = registrRESTApi;
    }


    @PostMapping(value = "/auth/registration", consumes = {"application/json"})
    ResponseEntity<?> register(@RequestBody User user) throws IOException {

        return registrRESTApi.register(user);
    }

    @GetMapping("/users")
    public CollectionModel<EntityModel<User>> index() throws IOException {
        return registrRESTApi.findAll();
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
