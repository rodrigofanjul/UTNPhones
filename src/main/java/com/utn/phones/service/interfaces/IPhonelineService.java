package com.utn.phones.service.interfaces;

import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Phoneline;
import com.utn.phones.model.User;

import java.util.List;

public interface IPhonelineService {
    List<Phoneline> getAll() throws ResourceNotFoundException;
    Phoneline getById(Long id) throws ResourceNotFoundException;
    Phoneline getByUser(User user) throws ResourceNotFoundException;
    Phoneline newPhoneline(Phoneline phoneline) throws ResourceAlreadyExistsException, ResourceNotFoundException;
}
