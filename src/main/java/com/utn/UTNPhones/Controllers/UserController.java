package com.utn.UTNPhones.Controllers;

import com.utn.UTNPhones.Exceptions.NullArgumentException;
import com.utn.UTNPhones.Exceptions.NotFoundException;
import com.utn.UTNPhones.Exceptions.AlreadyExistsException;
import com.utn.UTNPhones.Models.User;
import com.utn.UTNPhones.Services.Interfaces.IUserService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login/")
    public User Login(@RequestBody @NotNull User user) throws NullArgumentException, NumberFormatException, NotFoundException {
        if (user.hasNullAtribute()) throw new NullArgumentException();
        User u;
        u = userService.Login(user);
        return u;
    }

    @PostMapping("/register/")
    public User Register(@RequestBody @NotNull User user) throws NullArgumentException, AlreadyExistsException {
        if (user.hasNullAtribute()) throw new NullArgumentException();
        try {
           userService.Register(user);
        }
        catch (DataAccessException th) {
           ConstraintViolationException cve = (ConstraintViolationException) th.getCause();
           throw new AlreadyExistsException("user",user.getId());
        }
        return user;
    }
}
