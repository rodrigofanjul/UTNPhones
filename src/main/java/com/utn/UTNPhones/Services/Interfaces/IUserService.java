package com.utn.UTNPhones.Services.Interfaces;

import com.utn.UTNPhones.Exceptions.NotFoundException;
import com.utn.UTNPhones.Models.User;
import org.springframework.dao.DataAccessException;


public interface IUserService {
    public User Login(User user) throws NotFoundException;
    public User Register(User user)throws DataAccessException;
}
