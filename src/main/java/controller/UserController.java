package controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class UserController {
    final
    RegistrRESTApi registrRESTApi;

    public UserController(RegistrRESTApi registrRESTApi) {
        this.registrRESTApi = registrRESTApi;
    }


    @PostMapping(value = "/auth/registration", consumes = {"application/json"}, produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = User.class)))
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
