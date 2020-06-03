package com.utn.phones.security;

import com.utn.phones.controller.PhonelineController;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Phoneline;
import com.utn.phones.service.PhonelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("SecurityService")
public class SecurityService {

    @Autowired
    PhonelineService phonelineService;

    public boolean isSelfPhoneline(Authentication authentication, Long id) throws ResourceNotFoundException {
        return authentication.getPrincipal().equals(phonelineService.getById(id).getUser().getId().toString());
    }
}
