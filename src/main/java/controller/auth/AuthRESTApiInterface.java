package controller.auth;

import exceptions.DAOException;
import exceptions.EmailConflictException;
import exceptions.NotAuthorizedException;
import model.User;
import org.springframework.http.ResponseEntity;
import service.dto.RequestDto;

public interface AuthRESTApiInterface {

    ResponseEntity logout(String accessToken);
    ResponseEntity register(RequestDto<User> userRequestDto);
    ResponseEntity login(RequestDto<User> userRequestDto) throws DAOException, EmailConflictException;
}
