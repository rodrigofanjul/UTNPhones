package com.utn.UTNPhones.Services.Interfaces;

import com.utn.UTNPhones.Exceptions.ResourceNotFoundException;
import com.utn.UTNPhones.Models.City;

public interface ICityService {
    City getById(Integer id) throws ResourceNotFoundException;
}
