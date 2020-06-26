package com.utn.phones.controller.web;

import com.utn.phones.controller.CallController;
import com.utn.phones.controller.InvoiceController;
import com.utn.phones.controller.UserController;
import com.utn.phones.dto.MostCalledDto;
import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Call;
import com.utn.phones.model.Invoice;
import com.utn.phones.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

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
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userController.getUsers();
        return (users.size() > 0) ? ResponseEntity.ok(users) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @IsSelfUserOrEmployee
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@Validated @PathVariable @Min(1) int id) throws ResourceNotFoundException {
        return ResponseEntity.ok(userController.getUser(id));
    }

    @IsSelfUserOrEmployee
    @GetMapping("/{id}/calls")
    public ResponseEntity<List<Call>> getUserCalls(@Validated @PathVariable @Min(1) int id) throws ResourceNotFoundException {
        List<Call> calls = callController.getCallsByUser(userController.getUser(id));
        return (calls.size() > 0) ? ResponseEntity.ok(calls) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @IsSelfUserOrEmployee
    @GetMapping("/{id}/calls/between")
    public ResponseEntity<List<Call>> getUserCallsBetween(@Validated @PathVariable @Min(1) int id, @RequestParam Date start, @RequestParam Date end) throws ResourceNotFoundException {
        List<Call> calls = callController.getCallsByUserBetween(userController.getUser(id),start,end);
        return (calls.size() > 0) ? ResponseEntity.ok(calls) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @IsSelfUserOrEmployee
    @GetMapping("/{id}/calls/most-called")
    public ResponseEntity<List<MostCalledDto>> getCallsByUserMostCalled(@Validated @PathVariable @Min(1) int id) throws ResourceNotFoundException {
        List<MostCalledDto> mostCalled = callController.getCallsByUserMostCalled(userController.getUser(id));
        return (mostCalled.size() > 0) ? ResponseEntity.ok(mostCalled) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @IsSelfUserOrEmployee
    @GetMapping("/{id}/invoices")
    public ResponseEntity<List<Invoice>> getUserInvoices(@Validated @PathVariable @Min(1) int id) throws ResourceNotFoundException {
        List<Invoice> invoices = invoiceController.getInvoicesByUser(userController.getUser(id));
        return (invoices.size() > 0) ? ResponseEntity.ok(invoices) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @IsSelfUserOrEmployee
    @GetMapping("/{id}/invoices/between")
    public ResponseEntity<List<Invoice>> getUserInvoicesBetween(@Validated @PathVariable @Min(1) int id, @RequestParam Date start, @RequestParam Date end) throws ResourceNotFoundException {
        List<Invoice> invoices = invoiceController.getInvoicesByUserBetween(userController.getUser(id),start,end);
        return (invoices.size() > 0) ? ResponseEntity.ok(invoices) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    public ResponseEntity<User> newUser(@Valid @RequestBody @NotNull User user) throws ResourceAlreadyExistsException, ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userController.newUser(user));
    }

    @IsEmployee
    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody @NotNull User user) throws ResourceAlreadyExistsException, ResourceNotFoundException {
        return ResponseEntity.ok(userController.updateUser(user));
    }

    @IsEmployee
    @DeleteMapping
    public ResponseEntity<Object> deleteUser(@Valid @RequestBody @NotNull User user) throws ResourceNotFoundException {
        userController.deleteUser(user);
        return ResponseEntity.noContent().build();
    }
}
