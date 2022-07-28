package service;

import DAO.UserDAO;
import exceptions.EmailConflictException;

public class UserDAOServiceLogin extends UserDAOService{
    public UserDAOServiceLogin() {

    }

    @Override
    void validate(String email) throws EmailConflictException {

    }
}
