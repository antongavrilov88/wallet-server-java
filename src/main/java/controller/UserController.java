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
    public CollectionModel<EntityModel<User>> all() {
        return registrRESTApi.findAll();
    }

    @GetMapping("/users/{id}")
    public CollectionModel<EntityModel<User>> one(@PathVariable int id) {
        return registrRESTApi.findAll();
    }


}
