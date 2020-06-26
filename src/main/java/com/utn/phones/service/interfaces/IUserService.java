package com.utn.phones.service.interfaces;

import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.User;

import java.util.List;

public interface IUserService {
    List<User> getAll();
    User getById(int id) throws ResourceNotFoundException;
    User getByIdCard(int id) throws ResourceNotFoundException;
    User newUser(User user) throws ResourceAlreadyExistsException, ResourceNotFoundException;
    User updateUser(User user) throws ResourceAlreadyExistsException, ResourceNotFoundException;
    void deleteUser(User user) throws ResourceNotFoundException;
}
