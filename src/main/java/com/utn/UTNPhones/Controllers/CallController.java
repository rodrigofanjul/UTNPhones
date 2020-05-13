package com.utn.UTNPhones.Controllers;

import com.utn.UTNPhones.Exceptions.ResourceNotFoundException;
import com.utn.UTNPhones.Models.Call;
import com.utn.UTNPhones.Services.Interfaces.ICallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/calls")
public class CallController {

    private final ICallService callService;

    @Autowired
    public CallController(ICallService callService) {
        this.callService = callService;
    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @PostMapping("")
    public ResponseEntity<Call> registerCall(@Valid @RequestBody @NotNull Call call) throws ResourceNotFoundException {
        Call registeredCall = callService.registerCall(call);
        if (registeredCall == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(registeredCall);
    }
}
