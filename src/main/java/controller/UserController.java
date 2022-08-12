package controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import model.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.RequestResponseDto;


import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class UserController {
    final
    RegistrRESTApi registrRESTApi;
    final
    LoginRESTApi loginRESTApi;
    final
    LogoutRESTApi logoutRESTApi;

    public UserController(RegistrRESTApi registrRESTApi, LoginRESTApi loginRESTApi, LogoutRESTApi logoutRESTApi) {
        this.registrRESTApi = registrRESTApi;
        this.loginRESTApi = loginRESTApi;
        this.logoutRESTApi = logoutRESTApi;
    }


    @PostMapping(value = "/auth/registration", consumes = {"application/json"}, produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = RequestResponseDto.class)))
    ResponseEntity<?> register(@RequestBody RequestResponseDto user) throws IOException {
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

    @PostMapping(value = "/auth/login", consumes = {"application/json"}, produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = RequestResponseDto.class)))
    ResponseEntity<?> login(@RequestBody RequestResponseDto user) throws IOException {
        return loginRESTApi.login(user);
    }

}
