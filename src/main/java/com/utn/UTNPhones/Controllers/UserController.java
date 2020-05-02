package com.utn.UTNPhones.Controllers;

import com.utn.UTNPhones.Exceptions.NullArgumentException;
import com.utn.UTNPhones.Exceptions.NotFoundException;
import com.utn.UTNPhones.Exceptions.AlreadyExistsException;
import com.utn.UTNPhones.Models.User;
import com.utn.UTNPhones.Services.Interfaces.IUserService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    //No tengo que chequear si es @Valid porque solo se enviará Idcard y Password
    @PostMapping(value = "/login/")
    public User Login(@RequestBody @NotNull User user, Errors errors) throws NumberFormatException, NotFoundException {
        User u;
        u = userService.Login(user);
        return u;
    }

    //Chequeo si es @Valid, para poder corroborar que los @NotNull del modelo se cumplen, sino mando una excepcion
    @PostMapping("/register/")
    public User Register(@Valid @RequestBody @NotNull User user, Errors errors) throws NullArgumentException, AlreadyExistsException {
        if (errors.hasErrors()) throw new NullArgumentException(errors);
        userService.Register(user);
        return user;
    }
}
