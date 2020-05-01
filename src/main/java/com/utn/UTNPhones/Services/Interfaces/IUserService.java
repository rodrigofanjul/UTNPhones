package com.utn.UTNPhones.Services.Interfaces;

import com.utn.UTNPhones.Exceptions.UserDoesntExistException;
import com.utn.UTNPhones.Models.User;
import org.springframework.dao.DataAccessException;


public interface IUserService {
    public User Login(User user) throws UserDoesntExistException;
    public User Register(User user)throws DataAccessException;
}
