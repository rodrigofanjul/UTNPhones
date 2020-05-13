package com.utn.UTNPhones.Controllers;

import com.utn.UTNPhones.Exceptions.ResourceAlreadyExistsException;
import com.utn.UTNPhones.Models.Phoneline;
import com.utn.UTNPhones.Security.SecurityProvider;
import com.utn.UTNPhones.Services.Interfaces.IPhonelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/phonelines")
public class PhonelineController {

    private final IPhonelineService phonelineService;

    @Autowired
    public PhonelineController(IPhonelineService phonelineService) {
        this.phonelineService = phonelineService;
    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @PostMapping("")
    public ResponseEntity<Phoneline> registerPhoneline(@Valid @RequestBody @NotNull Phoneline phoneline) throws ResourceAlreadyExistsException {
        Phoneline registeredPhoneline = phonelineService.registerPhoneline(phoneline);
        if (registeredPhoneline == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(registeredPhoneline);
    }
}
