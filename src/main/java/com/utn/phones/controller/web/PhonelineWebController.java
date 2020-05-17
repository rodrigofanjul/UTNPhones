package com.utn.phones.controller.web;

import com.utn.phones.controller.PhonelineController;
import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Phoneline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.utn.phones.security.SecurityConfig.*;

@RestController
@RequestMapping("/api/phonelines")
public class PhonelineWebController {

    private final PhonelineController phonelineController;

    @Autowired
    public PhonelineWebController(PhonelineController phonelineController) {
        this.phonelineController = phonelineController;
    }

    @IsEmployee
    @PostMapping
    public ResponseEntity<Phoneline> newPhoneline(@Valid @RequestBody @NotNull Phoneline phoneline) throws ResourceAlreadyExistsException, ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(phonelineController.newPhoneline(phoneline));
    }
}
