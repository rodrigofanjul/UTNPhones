package com.utn.phones.service.interfaces;

import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Province;

public interface IProvinceService {
    Province getById(Integer id) throws ResourceNotFoundException;
}
