package com.utn.phones.controller.web;

import com.utn.phones.controller.CallController;
import com.utn.phones.controller.UserController;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Call;
import com.utn.phones.model.User;
import com.utn.phones.security.SecurityProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

import static com.utn.phones.security.SecurityConfig.*;

@RestController
@RequestMapping("/api/calls")
public class CallWebController {

    private final CallController callController;
    private final UserController userController;

    @Autowired
    public CallWebController(CallController callController, UserController userController) {
        this.callController = callController;
        this.userController = userController;
    }

    @IsEmployee
    @GetMapping
    public ResponseEntity<Object> getAll() throws ResourceNotFoundException {
        List<Call> calls = callController.getCalls();
        return (calls.size() > 0) ? ResponseEntity.ok(calls) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @IsSameUserOrEmployee
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserCalls(@Validated @PathVariable @Min(1) int id) throws ResourceNotFoundException {
        return ResponseEntity.ok(callController.getCallsByUser(userController.getUserById(id)));
    }

    @IsSameUserOrEmployee
    @GetMapping("/{id}/between")
    public ResponseEntity<Object> getUserCallsBetween(@Validated @PathVariable @Min(1) int id, @RequestParam Date start, @RequestParam Date end) throws ResourceNotFoundException {
        return ResponseEntity.ok(callController.getCallsByUserBetween(userController.getUserById(id),start,end));
    }

    @IsSameUserOrEmployee
    @GetMapping("/{id}/most-called")
    public ResponseEntity<Object> getUserMostCalled(@Validated @PathVariable @Min(1) int id) throws ResourceNotFoundException {
        User u = userController.getUserById(id);
        return ResponseEntity.ok(callController.getCallsByUserMostCalled(u));
    }

    @IsEmployee
    @PostMapping
    public ResponseEntity<Call> newCall(@Valid @RequestBody @NotNull Call call) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(callController.newCall(call));
    }
}
