package com.utn.phones.controller.web;

import com.utn.phones.controller.InvoiceController;
import com.utn.phones.controller.UserController;
import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static com.utn.phones.security.SecurityConfig.*;

@RestController
@RequestMapping("/api/users")
public class UserWebController {

    private final UserController userController;
    private final InvoiceController invoiceController;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserWebController(UserController userController, InvoiceController invoiceController, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userController = userController;
        this.invoiceController = invoiceController;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @IsEmployee
    @GetMapping
    public ResponseEntity<Object> getAll() throws ResourceNotFoundException {
        return ResponseEntity.ok(userController.getUsers());
    }

    @IsSameUserOrEmployee
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@Validated @PathVariable @Min(1) int id) throws ResourceNotFoundException {
        User u = userController.getUserById(id);
        return ResponseEntity.ok(u);
    }

    @IsEmployee
    @PostMapping
    public ResponseEntity<User> newUser(@Valid @RequestBody @NotNull User user) throws ResourceAlreadyExistsException, ResourceNotFoundException {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(userController.newUser(user));
    }

    @IsEmployee
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@Validated @PathVariable @Min(1) int id, @Valid @RequestBody @NotNull User user) throws ResourceAlreadyExistsException, ResourceNotFoundException {
        User updatedUser = userController.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }
}
