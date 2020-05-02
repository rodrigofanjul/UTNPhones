package com.utn.UTNPhones.Controllers;

import com.utn.UTNPhones.Services.Interfaces.IPhonelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/phoneline")
public class PhonelineController {

    private final IPhonelineService phonelineService;

    @Autowired
    public PhonelineController(IPhonelineService phonelineService) {
        this.phonelineService = phonelineService;
    }
}
