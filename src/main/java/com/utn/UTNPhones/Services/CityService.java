package com.utn.UTNPhones.Services;

import com.utn.UTNPhones.Exceptions.ResourceNotFoundException;
import com.utn.UTNPhones.Models.City;
import com.utn.UTNPhones.Repositories.ICityRepository;
import com.utn.UTNPhones.Services.Interfaces.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService implements ICityService {

    private final ICityRepository cityRepository;

    @Autowired
    public CityService(ICityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City getById(Integer id) throws ResourceNotFoundException {
        return cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource City not found with (id:%d)",id)));
    }
}
