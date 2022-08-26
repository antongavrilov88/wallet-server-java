package controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import model.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import service.dto.RequestDto;
import service.dto.ResponseDto;


import javax.servlet.http.HttpServletRequest;
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
    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    ResponseEntity<?> register(@RequestBody RequestDto user, HttpServletRequest request) throws IOException {
        user.setIssuer(request.getRequestURL().toString());
        return registrRESTApi.register(user);
    }

    @GetMapping("/users")
    public CollectionModel<EntityModel<User>> all() {
        return registrRESTApi.findAll();
    }

    @GetMapping("/users/{id}")
    public User one(@PathVariable int id) {
        return registrRESTApi.findById(id);
    }

    @PostMapping(value = "/auth/login", consumes = {"application/json"}, produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    ResponseEntity<?> login(@RequestBody RequestDto user) throws IOException {
        return loginRESTApi.login(user);
    }

    @Operation(security = @SecurityRequirement(name = "Bearer"))
    @PostMapping(value = "/auth/logout", consumes = {"application/json"}, produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    void logout(@Parameter(hidden = true) String token) throws IOException {
        //logoutRESTApi.logout(user);
    }

    @GetMapping("/index")
    ResponseEntity<?> index() {
        //TODO delete this method
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"message\": \"Бля, работает!!!\"}");
    }

}
