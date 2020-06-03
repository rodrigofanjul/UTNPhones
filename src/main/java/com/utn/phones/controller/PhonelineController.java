package com.utn.phones.controller;

import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Phoneline;
import com.utn.phones.service.interfaces.IPhonelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PhonelineController {

    private final IPhonelineService phonelineService;

    @Autowired
    public PhonelineController(IPhonelineService phonelineService) {
        this.phonelineService = phonelineService;
    }

    public List<Phoneline> getPhonelines() throws ResourceNotFoundException {
        return phonelineService.getAll();
    }

    public Phoneline getPhonelineById(Long id) throws ResourceNotFoundException {
        return phonelineService.getById(id);
    }

    public Phoneline newPhoneline(Phoneline phoneline) throws ResourceAlreadyExistsException {
        return phonelineService.newPhoneline(phoneline);
    }
}
