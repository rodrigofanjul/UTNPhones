package com.utn.UTNPhones.Services.Interfaces;

import com.utn.UTNPhones.Exceptions.AlreadyExistsException;
import com.utn.UTNPhones.Exceptions.IncorrectPasswordException;
import com.utn.UTNPhones.Exceptions.NotFoundException;
import com.utn.UTNPhones.Models.User;
import org.springframework.dao.DataAccessException;


public interface IUserService {
    public User loginUser(User user) throws NotFoundException, DataAccessException, IncorrectPasswordException;
    public User registerUser(User user) throws AlreadyExistsException, DataAccessException;
    public User updateUser(int id, User user) throws DataAccessException;
}
