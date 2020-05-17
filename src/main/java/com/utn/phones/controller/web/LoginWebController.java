package com.utn.phones.controller.web;

import com.utn.phones.controller.UserController;
import com.utn.phones.exception.InvalidLoginException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.User;
import com.utn.phones.security.SecurityProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/auth")
public class LoginWebController {

    private final UserController userController;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public LoginWebController(UserController userController, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userController = userController;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    @PostMapping
    public ResponseEntity<Object> loginUser(@RequestBody @NotNull User user) throws InvalidLoginException, ResourceNotFoundException {
        User loggedUser = userController.getUserByIdCard(user.getIdcard());
        if(!bCryptPasswordEncoder.matches(user.getPassword(),loggedUser.getPassword())) throw new InvalidLoginException();
        String token = SecurityProvider.tokenProvider(loggedUser.getId().toString(),loggedUser.getRole().toString());
        return ResponseEntity.ok().headers(createHeaders(token)).build();
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return headers;
    }
}
