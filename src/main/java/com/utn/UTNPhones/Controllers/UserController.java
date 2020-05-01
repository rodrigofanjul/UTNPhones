package com.utn.UTNPhones.Controllers;

import com.utn.UTNPhones.Exceptions.ParametersException;
import com.utn.UTNPhones.Exceptions.UserDoesntExistException;
import com.utn.UTNPhones.Exceptions.UserExistsException;
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
    public User login(@RequestBody @NotNull User user) throws ParametersException, NumberFormatException, UserDoesntExistException {
        User u;
        if (user.getIdentification()==null || user.getPassword()==null){
            throw new ParametersException();
        }else{
            u = userService.Login(user);
        }
         return u;
    }

    @PostMapping("/register/")
    public User register(@RequestBody @NotNull User user) throws ParametersException, UserExistsException {
       if (user.hasNullAtribute()){
          throw new ParametersException();
       }else{
           try {
               userService.Register(user);
           } catch (DataAccessException th) {

               ConstraintViolationException cve= (ConstraintViolationException) th.getCause();

               throw new UserExistsException();
           }
       }
        return user;
    }
}
