package com.utn.UTNPhones.Controllers;

import com.utn.UTNPhones.Exceptions.AlreadyExistsException;
import com.utn.UTNPhones.Models.Phoneline;
import com.utn.UTNPhones.Services.Interfaces.IPhonelineService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/register")
    public Phoneline registerPhoneline(@Valid @RequestBody @NotNull Phoneline phoneline) throws AlreadyExistsException {
        phoneline = phonelineService.register(phoneline);
        return phoneline;
    }
}
