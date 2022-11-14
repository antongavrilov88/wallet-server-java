package service;

import exceptions.*;
import model.User;
import service.dto.EntityDto;
import service.dto.RequestDto;

public interface AuthServiceInterface {

    EntityDto<User> register(RequestDto<User> requestDto) throws WrongRequestType, NotValidEmailException, EmailConflictException, DAOException;
    EntityDto<User> login(RequestDto<User> entity) throws NotValidEmailException, DAOException, WrongPasswordException, UserNotFoundException;
    void logout(String accessToken) throws NotAuthorizedException;
}
