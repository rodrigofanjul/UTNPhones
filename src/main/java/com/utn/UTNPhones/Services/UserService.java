package com.utn.UTNPhones.Services;

import com.utn.UTNPhones.Exceptions.ResourceAlreadyExistsException;
import com.utn.UTNPhones.Exceptions.ResourceNotFoundException;
import com.utn.UTNPhones.Models.City;
import com.utn.UTNPhones.Models.Province;
import com.utn.UTNPhones.Models.User;
import com.utn.UTNPhones.Repositories.IUserRepository;
import com.utn.UTNPhones.Services.Interfaces.ICityService;
import com.utn.UTNPhones.Services.Interfaces.IProvinceService;
import com.utn.UTNPhones.Services.Interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final ICityService cityService;
    private final IProvinceService provinceService;

    @Autowired
    public UserService(IUserRepository userRepository, ICityService cityService, IProvinceService provinceService) {
        this.userRepository = userRepository;
        this.cityService = cityService;
        this.provinceService = provinceService;
    }

    public List<User> getAll() throws ResourceNotFoundException {
        return Optional.ofNullable(userRepository.findAll())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resources User not found")));
    }

    public User getById(int id) throws ResourceNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource User not found with (id:%d)",id)));
    }

    public User getByIdcard(int idcard) throws ResourceNotFoundException {
        return Optional.ofNullable(userRepository.findByIdcard(idcard))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource User not found with (idCard:%d)",idcard)));
    }

    public User registerUser(User user) throws ResourceAlreadyExistsException, ResourceNotFoundException {
        if(userRepository.findByIdcard(user.getIdcard()) != null)
            throw new ResourceAlreadyExistsException(String.format("Resource User already exists with (id:%d)", user.getIdcard()));
        City city = cityService.getById(user.getCity().getId());
        Province province = provinceService.getById(city.getProvince().getId());
        return userRepository.save(user);
    }

    public User updateUser(int id, User user) throws ResourceAlreadyExistsException, ResourceNotFoundException {
        User u = userRepository.findByIdcard(user.getIdcard());
        if(u != null && u.getId() != id)
            throw new ResourceAlreadyExistsException(String.format("Resource User already exists with (idCard:%d)", user.getIdcard()));
        City city = cityService.getById(user.getCity().getId());
        Province province = provinceService.getById(city.getProvince().getId());
        user.setId(id);
        return userRepository.save(user);
    }
}