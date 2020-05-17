package com.utn.phones.service.interfaces;

import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.City;

public interface ICityService {
    City getById(Integer id) throws ResourceNotFoundException;
}
