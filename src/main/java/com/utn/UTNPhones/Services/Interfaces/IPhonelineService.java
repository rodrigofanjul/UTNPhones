package com.utn.UTNPhones.Services.Interfaces;

import com.utn.UTNPhones.Exceptions.ResourceAlreadyExistsException;
import com.utn.UTNPhones.Models.Phoneline;
import com.utn.UTNPhones.Models.User;

public interface IPhonelineService {
    Phoneline getById(Long id);
    Phoneline getByUser(User user);
    Phoneline registerPhoneline(Phoneline phoneline) throws ResourceAlreadyExistsException;
}
