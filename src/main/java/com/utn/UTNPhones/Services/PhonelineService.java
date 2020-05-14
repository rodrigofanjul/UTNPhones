package com.utn.UTNPhones.Services;

import com.utn.UTNPhones.Exceptions.ResourceAlreadyExistsException;
import com.utn.UTNPhones.Exceptions.ResourceNotFoundException;
import com.utn.UTNPhones.Models.Phoneline;
import com.utn.UTNPhones.Models.User;
import com.utn.UTNPhones.Repositories.IPhonelineRepository;
import com.utn.UTNPhones.Services.Interfaces.IPhonelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PhonelineService implements IPhonelineService {

    private final IPhonelineRepository phonelineRepository;

    @Autowired
    public PhonelineService(IPhonelineRepository phonelineRepository) {
        this.phonelineRepository = phonelineRepository;
    }

    public Phoneline getById(Long id) throws ResourceNotFoundException {
        return Optional.ofNullable(phonelineRepository.findById(id))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource Phoneline not found with (id:%d)", id)));
    }

    public Phoneline getByUser(User user) throws ResourceNotFoundException {
        return Optional.ofNullable(phonelineRepository.findByUser(user))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource Phoneline not found with User (id:%d)", user.getId())));
    }

    public Phoneline registerPhoneline(Phoneline phoneline) throws ResourceAlreadyExistsException, ResourceNotFoundException {
        if(phonelineRepository.findById(phoneline.getId()) != null)
            throw new ResourceAlreadyExistsException(String.format("Resource Phoneline already exists with (id:%d)", phoneline.getId()));
        return phonelineRepository.save(phoneline);
    }
}
