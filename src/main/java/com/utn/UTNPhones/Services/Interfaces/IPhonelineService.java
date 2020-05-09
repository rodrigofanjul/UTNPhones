package com.utn.UTNPhones.Services.Interfaces;

import com.utn.UTNPhones.Exceptions.AlreadyExistsException;
import com.utn.UTNPhones.Models.Phoneline;

public interface IPhonelineService {
    Phoneline register(Phoneline phoneline) throws AlreadyExistsException;
}
