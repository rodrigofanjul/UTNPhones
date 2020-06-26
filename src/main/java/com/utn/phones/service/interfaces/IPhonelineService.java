package com.utn.phones.service.interfaces;

import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Phoneline;
import com.utn.phones.model.User;

import java.util.List;

public interface IPhonelineService {
    List<Phoneline> getAll();
    Phoneline getById(Long id) throws ResourceNotFoundException;
    List<Phoneline> getByUser(User user) throws ResourceNotFoundException;
    Phoneline newPhoneline(Phoneline phoneline) throws ResourceAlreadyExistsException;
}
