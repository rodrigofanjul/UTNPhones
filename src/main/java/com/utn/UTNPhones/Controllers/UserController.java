package com.utn.UTNPhones.Controllers;

import com.utn.UTNPhones.Exceptions.AlreadyExistsException;
import com.utn.UTNPhones.Exceptions.IncorrectPasswordException;
import com.utn.UTNPhones.Exceptions.NotFoundException;
import com.utn.UTNPhones.Models.Call;
import com.utn.UTNPhones.Models.Invoice;
import com.utn.UTNPhones.Models.User;
import com.utn.UTNPhones.Services.Interfaces.ICallService;
import com.utn.UTNPhones.Services.Interfaces.IInvoiceService;
import com.utn.UTNPhones.Services.Interfaces.IUserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
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
    public UserController(IUserService userService, IInvoiceService invoiceService, ICallService callService) {
        this.userService = userService;
        this.invoiceService = invoiceService;
        this.callService = callService;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping("/{id}/invoices")
    public List<Invoice> getUserInvoices(@Validated @PathVariable @Min(1) int id) throws NotFoundException {
        User u = new User();
        u.setId(id);
        List<Invoice> invoice = invoiceService.getUserInvoices(u);
        return invoice;
    }

    @GetMapping("/{id}/invoices/between")
    public List<Invoice> getUserInvoicesBetween(@Validated @PathVariable @Min(1) int id, @RequestParam Date start, @RequestParam Date end) throws NotFoundException {
        User u = new User();
        u.setId(id);
        List<Invoice> invoice = invoiceService.getUserInvoicesBetween(u,start,end);
        return invoice;
    }

    @GetMapping("/{id}/calls")
    public List<Call> getUserCalls(@Validated @PathVariable @Min(1) int id) throws NotFoundException {
        User u = new User();
        u.setId(id);
        List<Call> call = callService.getUserCalls(u);
        return call;
    }

    @GetMapping("/{id}/calls/between")
    public List<Call> getUserCallsBetween(@Validated @PathVariable @Min(1) int id, @RequestParam Date start, @RequestParam Date end) throws NotFoundException {
        User u = new User();
        u.setId(id);
        List<Call> call = callService.getUserCallsBetween(u,start,end);
        return call;
    }

    @GetMapping("/{id}/calls/most-called")
    public List<Call> getUserMostCalled(@Validated @PathVariable @Min(1) int id) throws NotFoundException {
        User u = new User();
        u.setId(id);
        List<Call> call = callService.getUserMostCalled(u);
        return call;
    }

    //No tengo que chequear si es @Valid porque solo se enviará Idcard y Password
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody @NotNull User user) throws NotFoundException, IncorrectPasswordException {
        User loggedUser = userService.loginUser(user);
        if(!bCryptPasswordEncoder.matches(user.getPassword(),loggedUser.getPassword())) throw new IncorrectPasswordException();
        Map<String,Object> body = new LinkedHashMap<>();
        body.put("user", loggedUser);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    //Chequeo si es @Valid, para corroborar que los @NotNull del modelo se cumplen, sino tiro una excepcion
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody @NotNull User user) throws AlreadyExistsException {
        User registeredUser = userService.registerUser(user);
        Map<String,Object> body = new LinkedHashMap<>();
        body.put("user", registeredUser);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@Validated @PathVariable @Min(1) int id, @Valid @RequestBody @NotNull User user) throws AlreadyExistsException {
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(updatedUser);
        }
    }
}
