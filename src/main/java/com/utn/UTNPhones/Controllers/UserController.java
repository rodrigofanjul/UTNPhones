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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @GetMapping("")
    public ResponseEntity<Object> getAll() throws ResourceNotFoundException {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@Validated @PathVariable @Min(1) int id) throws ResourceNotFoundException {
        User u = userService.getById(id);
        return ResponseEntity.ok(u);
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('EMPLOYEE')")
    @GetMapping("/{id}/invoices")
    public ResponseEntity<Object> getUserInvoices(@Validated @PathVariable @Min(1) int id) throws ResourceNotFoundException {
        User u = userService.getById(id);
        if(SecurityProvider.hasAuthority("USER") && !SecurityProvider.isUser(u.getIdcard().toString()))
            ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        List<Invoice> invoices = invoiceService.getUserInvoices(u);
        return ResponseEntity.ok(invoices);
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('EMPLOYEE')")
    @GetMapping("/{id}/invoices/between")
    public ResponseEntity<Object> getUserInvoicesBetween(@Validated @PathVariable @Min(1) int id, @RequestParam Date start, @RequestParam Date end) throws ResourceNotFoundException {
        User u = userService.getById(id);
        if(SecurityProvider.hasAuthority("USER") && !SecurityProvider.isUser(u.getIdcard().toString()))
            ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        List<Invoice> invoices = invoiceService.getUserInvoicesBetween(u,start,end);
        return ResponseEntity.ok(invoices);
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('EMPLOYEE')")
    @GetMapping("/{id}/calls")
    public ResponseEntity<Object> getUserCalls(@Validated @PathVariable @Min(1) int id) throws ResourceNotFoundException {
        User u = userService.getById(id);
        if(SecurityProvider.hasAuthority("USER") && !SecurityProvider.isUser(u.getIdcard().toString()))
            ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        List<Call> calls = callService.getUserCalls(u);
        return ResponseEntity.ok(calls);
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('EMPLOYEE')")
    @GetMapping("/{id}/calls/between")
    public ResponseEntity<Object> getUserCallsBetween(@Validated @PathVariable @Min(1) int id, @RequestParam Date start, @RequestParam Date end) throws ResourceNotFoundException {
        User u = userService.getById(id);
        if(SecurityProvider.hasAuthority("USER") && !SecurityProvider.isUser(u.getIdcard().toString()))
            ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        List<Call> calls = callService.getUserCallsBetween(u,start,end);
        return ResponseEntity.ok(calls);
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('EMPLOYEE')")
    @GetMapping("/{id}/calls/most-called")
    public ResponseEntity<Object> getUserMostCalled(@Validated @PathVariable @Min(1) int id) throws ResourceNotFoundException {
        User u = userService.getById(id);
        if(SecurityProvider.hasAuthority("USER") && !SecurityProvider.isUser(u.getIdcard().toString()))
            ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        List<Call> calls = callService.getUserMostCalled(u);
        return ResponseEntity.ok(calls);
    }

    //Chequeo si es @Valid, para corroborar que los @NotNull del modelo se cumplen, sino tiro una excepcion
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody @NotNull User user) throws ResourceAlreadyExistsException, ResourceNotFoundException {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    //No tengo que chequear si es @Valid porque solo se enviará Idcard y Password
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody @NotNull User user) throws IncorrectPasswordException, ResourceNotFoundException {
        User loggedUser = userService.getByIdcard(user.getIdcard());
        if(!bCryptPasswordEncoder.matches(user.getPassword(),loggedUser.getPassword())) throw new IncorrectPasswordException();
        Map<String,Object> body = new LinkedHashMap<>();
        body.put("user", loggedUser);
        body.put("token", SecurityProvider.tokenProvider(loggedUser.getIdcard().toString(),loggedUser.getRole().toString()));
        return ResponseEntity.ok(body);
    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@Validated @PathVariable @Min(1) int id, @Valid @RequestBody @NotNull User user) throws ResourceAlreadyExistsException, ResourceNotFoundException {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }
}
