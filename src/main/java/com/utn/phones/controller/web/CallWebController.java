package com.utn.phones.controller.web;

import com.utn.phones.controller.CallController;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Call;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.utn.phones.security.SecurityConfig.IsEmployee;
import static com.utn.phones.security.SecurityConfig.IsInfraestructure;

@RestController
@RequestMapping("/api/calls")
public class CallWebController {

    private final CallController callController;

    @Autowired
    public CallWebController(CallController callController) {
        this.callController = callController;
    }

    @IsEmployee
    @GetMapping
    public ResponseEntity<List<Call>> getCalls() {
        List<Call> calls = callController.getCalls();
        return (calls.size() > 0) ? ResponseEntity.ok(calls) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @IsInfraestructure
    @PostMapping
    public ResponseEntity<Call> newCall(@Valid @RequestBody @NotNull Call call) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(callController.newCall(call));
    }
}
