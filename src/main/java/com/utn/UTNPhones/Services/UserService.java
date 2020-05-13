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

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getByIdcard(int idcard) {
        return userRepository.findByIdcard(idcard);
    }

    public User registerUser(User user) throws ResourceAlreadyExistsException, ResourceNotFoundException {
        if(userRepository.findByIdcard(user.getIdcard()) != null)
            throw new ResourceAlreadyExistsException(String.format("Resource User already exists with (id:%d)", user.getIdcard()));
        City city = cityService.getById(user.getCity().getId());
        if(city == null)
            throw new ResourceNotFoundException(String.format("Resource City not found with (id:%d)",user.getCity().getId()));
        Province province = provinceService.getById(city.getProvince().getId());
        if(province == null)
            throw new ResourceNotFoundException(String.format("Resource Province not found with (id:%d)",user.getCity().getId()));
        return userRepository.save(user);
    }

    public User updateUser(int id, User user) throws ResourceAlreadyExistsException, ResourceNotFoundException {
        user.setId(id);
        if(userRepository.findByIdcard(user.getIdcard()) != null)
            throw new ResourceAlreadyExistsException(String.format("Resource User already exists with (id:%d)", user.getIdcard()));
        City city = cityService.getById(user.getCity().getId());
        if(city == null)
            throw new ResourceNotFoundException(String.format("Resource City not found with (id:%d)",user.getCity().getId()));
        Province province = provinceService.getById(city.getProvince().getId());
        if(province == null)
            throw new ResourceNotFoundException(String.format("Resource Province not found with (id:%d)",user.getCity().getId()));
        return userRepository.save(user);
    }
}