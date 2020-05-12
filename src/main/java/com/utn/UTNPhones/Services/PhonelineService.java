package com.utn.UTNPhones.Services;

import com.utn.UTNPhones.Exceptions.ResourceAlreadyExistsException;
import com.utn.UTNPhones.Models.Phoneline;
import com.utn.UTNPhones.Models.User;
import com.utn.UTNPhones.Repositories.IPhonelineRepository;
import com.utn.UTNPhones.Services.Interfaces.IPhonelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhonelineService implements IPhonelineService {

    private final IPhonelineRepository phonelineRepository;

    @Autowired
    public PhonelineService(IPhonelineRepository phonelineRepository) {
        this.phonelineRepository = phonelineRepository;
    }

    public Phoneline getById(Long id) {
        return phonelineRepository.findById(id);
    }

    public Phoneline getByUser(User user) {
        return phonelineRepository.findByUser(user);
    }

    public Phoneline registerPhoneline(Phoneline phoneline) throws ResourceAlreadyExistsException {
        if(getById(phoneline.getId()) != null)
            throw new ResourceAlreadyExistsException(String.format("Resource phoneline id %d already exists", phoneline.getId()));
        return phonelineRepository.save(phoneline);
    }
}
