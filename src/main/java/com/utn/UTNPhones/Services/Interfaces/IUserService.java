package com.utn.UTNPhones.Services.Interfaces;

import com.utn.UTNPhones.Exceptions.ResourceAlreadyExistsException;
import com.utn.UTNPhones.Exceptions.ResourceNotFoundException;
import com.utn.UTNPhones.Models.User;

import java.util.List;

public interface IUserService {
    User registerUser(User user) throws ResourceAlreadyExistsException, ResourceNotFoundException;
    User updateUser(int id, User user) throws ResourceAlreadyExistsException, ResourceNotFoundException;
    List<User> getAll();
    User getById(int id);
    User getByIdcard(int idcard);
}
