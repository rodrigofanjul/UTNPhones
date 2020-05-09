package com.utn.UTNPhones.Services;

import com.utn.UTNPhones.Exceptions.AlreadyExistsException;
import com.utn.UTNPhones.Models.Phoneline;
import com.utn.UTNPhones.Repositories.IPhonelineRepository;
import com.utn.UTNPhones.Services.Interfaces.IPhonelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PhonelineService implements IPhonelineService {

    private final IPhonelineRepository phonelineRepository;

    @Autowired
    public PhonelineService(IPhonelineRepository phonelineRepository) {
        this.phonelineRepository = phonelineRepository;
    }

    public Phoneline register(Phoneline phoneline) throws AlreadyExistsException {
        try {
            return phonelineRepository.save(phoneline);
        }
        catch (Exception e) {
            throw new AlreadyExistsException();
        }
    }
}
