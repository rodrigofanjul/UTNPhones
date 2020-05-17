package com.utn.phones.controller;

import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.User;
import com.utn.phones.model.User;
import com.utn.phones.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    public List<User> getUsers() throws ResourceNotFoundException {
        return userService.getAll();
    }

    public User getUserById(int id) throws ResourceNotFoundException {
        return userService.getById(id);
    }

    public User getUserByIdCard(int idCard) throws ResourceNotFoundException {
        return userService.getByIdCard(idCard);
    }

    public User newUser(User user) throws ResourceNotFoundException, ResourceAlreadyExistsException {
        return userService.newUser(user);
    }

    public User updateUser(int id, User user) throws ResourceNotFoundException, ResourceAlreadyExistsException {
        return userService.updateUser(id, user);
    }
}
