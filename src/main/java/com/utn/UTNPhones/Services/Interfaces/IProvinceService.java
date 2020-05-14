package com.utn.UTNPhones.Services.Interfaces;

import com.utn.UTNPhones.Exceptions.ResourceNotFoundException;
import com.utn.UTNPhones.Models.Province;

public interface IProvinceService {
    Province getById(Integer id) throws ResourceNotFoundException;
}
