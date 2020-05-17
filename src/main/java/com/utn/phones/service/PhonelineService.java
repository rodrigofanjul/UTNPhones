package com.utn.phones.service;

import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Phoneline;
import com.utn.phones.model.User;
import com.utn.phones.repository.IPhonelineRepository;
import com.utn.phones.service.interfaces.IPhonelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhonelineService implements IPhonelineService {

    private final IPhonelineRepository phonelineRepository;

    @Autowired
    public PhonelineService(IPhonelineRepository phonelineRepository) {
        this.phonelineRepository = phonelineRepository;
    }

    public List<Phoneline> getAll() throws ResourceNotFoundException {
        return Optional.ofNullable(phonelineRepository.findAll())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource Phoneline not found")));
    }

    public Phoneline getById(Long id) throws ResourceNotFoundException {
        return Optional.ofNullable(phonelineRepository.findById(id))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource Phoneline not found with (id:%d)", id)));
    }

    public Phoneline getByUser(User user) throws ResourceNotFoundException {
        return Optional.ofNullable(phonelineRepository.findByUser(user))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource Phoneline not found with User (id:%d)", user.getId())));
    }

    public Phoneline newPhoneline(Phoneline phoneline) throws ResourceAlreadyExistsException, ResourceNotFoundException {
        if(phonelineRepository.findById(phoneline.getId()) != null)
            throw new ResourceAlreadyExistsException(String.format("Resource Phoneline already exists with (id:%d)", phoneline.getId()));
        return phonelineRepository.save(phoneline);
    }
}