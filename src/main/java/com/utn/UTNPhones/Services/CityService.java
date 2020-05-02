package com.utn.UTNPhones.Services;

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
}
