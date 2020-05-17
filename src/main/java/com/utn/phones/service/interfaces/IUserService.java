package com.utn.phones.service.interfaces;

import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.User;

import java.util.List;

public interface IUserService {
    List<User> getAll() throws ResourceNotFoundException;
    User getById(int id) throws ResourceNotFoundException;
    User getByIdCard(int id) throws ResourceNotFoundException;
    User newUser(User user) throws ResourceAlreadyExistsException, ResourceNotFoundException;
    User updateUser(int id, User user) throws ResourceAlreadyExistsException, ResourceNotFoundException;
}
