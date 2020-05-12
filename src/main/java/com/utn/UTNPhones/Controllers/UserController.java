package com.utn.UTNPhones.Controllers;

import com.utn.UTNPhones.Exceptions.ResourceAlreadyExistsException;
import com.utn.UTNPhones.Exceptions.IncorrectPasswordException;
import com.utn.UTNPhones.Exceptions.ResourceNotFoundException;
import com.utn.UTNPhones.Models.Call;
import com.utn.UTNPhones.Models.Invoice;
import com.utn.UTNPhones.Models.User;
import com.utn.UTNPhones.Security.SecurityProvider;
import com.utn.UTNPhones.Services.Interfaces.ICallService;
import com.utn.UTNPhones.Services.Interfaces.IInvoiceService;
import com.utn.UTNPhones.Services.Interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;
    private final IInvoiceService invoiceService;
    private final ICallService callService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(IUserService userService, IInvoiceService invoiceService, ICallService callService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.invoiceService = invoiceService;
        this.callService = callService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("")
    public ResponseEntity<Object> getAll() {
        List<User> users = userService.getAll();
        if (users == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@Validated @PathVariable @Min(1) int id) {
        User u = userService.getById(id);
        if (u == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(u);
    }

    @GetMapping("/{id}/invoices")
    public ResponseEntity<Object> getUserInvoices(@Validated @PathVariable @Min(1) int id) {
        User u = userService.getById(id);
        if (u == null) return ResponseEntity.notFound().build();
        List<Invoice> invoices = invoiceService.getUserInvoices(u);
        if(invoices == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/{id}/invoices/between")
    public ResponseEntity<Object> getUserInvoicesBetween(@Validated @PathVariable @Min(1) int id, @RequestParam Date start, @RequestParam Date end) {
        User u = userService.getById(id);
        if (u == null) return ResponseEntity.notFound().build();
        List<Invoice> invoices = invoiceService.getUserInvoicesBetween(u,start,end);
        if(invoices == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/{id}/calls")
    public ResponseEntity<Object> getUserCalls(@Validated @PathVariable @Min(1) int id) {
        User u = userService.getById(id);
        if (u == null) return ResponseEntity.notFound().build();
        List<Call> calls = callService.getUserCalls(u);
        if(calls == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(calls);
    }

    @GetMapping("/{id}/calls/between")
    public ResponseEntity<Object> getUserCallsBetween(@Validated @PathVariable @Min(1) int id, @RequestParam Date start, @RequestParam Date end) {
        User u = userService.getById(id);
        if (u == null) return ResponseEntity.notFound().build();
        List<Call> calls = callService.getUserCallsBetween(u,start,end);
        if(calls == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(calls);
    }

    @GetMapping("/{id}/calls/most-called")
    public ResponseEntity<Object> getUserMostCalled(@Validated @PathVariable @Min(1) int id) {
        User u = userService.getById(id);
        if (u == null) return ResponseEntity.notFound().build();
        List<Call> calls = callService.getUserMostCalled(u);
        return ResponseEntity.ok(calls);
    }

    //No tengo que chequear si es @Valid porque solo se enviará Idcard y Password
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody @NotNull User user) throws IncorrectPasswordException {
        User loggedUser = userService.getByIdcard(user.getIdcard());
        if (loggedUser == null) return ResponseEntity.notFound().build();
        if(!bCryptPasswordEncoder.matches(user.getPassword(),loggedUser.getPassword())) throw new IncorrectPasswordException();

        Map<String,Object> body = new LinkedHashMap<>();
        body.put("user", loggedUser);
        body.put("token", SecurityProvider.tokenProvider(loggedUser.getIdcard().toString(),loggedUser.getRole().toString()));
        return ResponseEntity.ok(body);
    }

    //Chequeo si es @Valid, para corroborar que los @NotNull del modelo se cumplen, sino tiro una excepcion
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody @NotNull User user) throws ResourceAlreadyExistsException, ResourceNotFoundException {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User registeredUser = userService.registerUser(user);
        if (registeredUser == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(registeredUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@Validated @PathVariable @Min(1) int id, @Valid @RequestBody @NotNull User user) throws ResourceAlreadyExistsException {
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updatedUser);
    }
}
