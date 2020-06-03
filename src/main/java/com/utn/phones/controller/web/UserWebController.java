package com.utn.phones.controller.web;

import com.utn.phones.controller.CallController;
import com.utn.phones.controller.InvoiceController;
import com.utn.phones.controller.UserController;
import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Invoice;
import com.utn.phones.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.util.Date;

import static com.utn.phones.security.SecurityConfig.*;

@RestController
@RequestMapping("/api/users")
public class UserWebController {

    private final UserController userController;
    private final InvoiceController invoiceController;
    private final CallController callController;

    @Autowired
    public UserWebController(UserController userController, InvoiceController invoiceController, CallController callController) {
        this.userController = userController;
        this.invoiceController = invoiceController;
        this.callController = callController;
    }

    @IsEmployee
    @GetMapping
    public ResponseEntity<Object> getAll() throws ResourceNotFoundException {
        return ResponseEntity.ok(userController.getUsers());
    }

    @IsSelfUserOrEmployee
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@Validated @PathVariable @Min(1) int id) throws ResourceNotFoundException {
        return ResponseEntity.ok(userController.getUserById(id));
    }

    @IsSelfUserOrEmployee
    @GetMapping("/{id}/calls")
    public ResponseEntity<Object> getUserCalls(@Validated @PathVariable @Min(1) int id) throws ResourceNotFoundException {
        return ResponseEntity.ok(callController.getCallsByUser(userController.getUserById(id)));
    }

    @IsSelfUserOrEmployee
    @GetMapping("/{id}/calls/between")
    public ResponseEntity<Object> getUserCallsBetween(@Validated @PathVariable @Min(1) int id, @RequestParam Date start, @RequestParam Date end) throws ResourceNotFoundException {
        return ResponseEntity.ok(callController.getCallsByUserBetween(userController.getUserById(id),start,end));
    }

    @IsSelfUserOrEmployee
    @GetMapping("/{id}/calls/most-called")
    public ResponseEntity<Object> getUserMostCalled(@Validated @PathVariable @Min(1) int id) throws ResourceNotFoundException {
        return ResponseEntity.ok(callController.getCallsByUserMostCalled(userController.getUserById(id)));
    }

    @IsSelfUserOrEmployee
    @GetMapping("/{id}/invoices")
    public ResponseEntity<Object> getUserInvoices(@Validated @PathVariable @Min(1) int id) throws ResourceNotFoundException {
        return ResponseEntity.ok(invoiceController.getInvoicesByUser(userController.getUserById(id)));
    }

    @IsSelfUserOrEmployee
    @GetMapping("/{id}/invoices/between")
    public ResponseEntity<Object> getUserInvoicesBetween(@Validated @PathVariable @Min(1) int id, @RequestParam Date start, @RequestParam Date end) throws ResourceNotFoundException {
        return ResponseEntity.ok(invoiceController.getInvoicesByUserBetween(userController.getUserById(id),start,end));
    }

    @PostMapping
    public ResponseEntity<User> newUser(@Valid @RequestBody @NotNull User user) throws ResourceAlreadyExistsException, ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userController.newUser(user));
    }

    @IsEmployee
    @PutMapping
    public ResponseEntity<User> updateUser(@Validated @PathVariable @Min(1) int id, @Valid @RequestBody @NotNull User user) throws ResourceAlreadyExistsException, ResourceNotFoundException {
        return ResponseEntity.ok(userController.updateUser(user));
    }
}
