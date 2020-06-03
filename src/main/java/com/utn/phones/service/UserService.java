package com.utn.phones.service;

import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.User;
import com.utn.phones.repository.IUserRepository;
import com.utn.phones.service.interfaces.ICityService;
import com.utn.phones.service.interfaces.IProvinceService;
import com.utn.phones.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final ICityService cityService;
    private final IProvinceService provinceService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(IUserRepository userRepository, ICityService cityService, IProvinceService provinceService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.cityService = cityService;
        this.provinceService = provinceService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<User> getAll() throws ResourceNotFoundException {
        return Optional.ofNullable(userRepository.findAll())
                .orElseThrow(() -> new ResourceNotFoundException());
    }

    public User getById(int id) throws ResourceNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource User not found with (id:%d)",id));
    }

    public User getByIdCard(int idCard) throws ResourceNotFoundException {
        return Optional.ofNullable(userRepository.findByIdcard(idCard))
                .orElseThrow(() -> new ResourceNotFoundException("Resource User not found with (idCard:%d)",idCard));
    }

    public User newUser(User user) throws ResourceAlreadyExistsException, ResourceNotFoundException {
        if(userRepository.findByIdcard(user.getIdcard()) != null)
            throw new ResourceAlreadyExistsException("Resource User already exists with (idCard:%d)", user.getIdcard());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCity(cityService.getById(user.getCity().getId()));
        return userRepository.save(user);
    }

    public User updateUser(User user) throws ResourceAlreadyExistsException, ResourceNotFoundException {
        if(!userRepository.existsById(user.getId()))
            throw new ResourceNotFoundException("Resource User not found with (id:%d)",user.getId());
        User u = userRepository.findByIdcard(user.getIdcard());
        if(u != null && u.getId() != user.getId())
            throw new ResourceAlreadyExistsException("Resource User already exists with (idCard:%d)", user.getIdcard());
        if(user.getPassword() != null) user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCity(cityService.getById(user.getCity().getId()));
        return userRepository.save(user);
    }
}