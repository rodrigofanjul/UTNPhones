package com.utn.phones.service.interfaces;

import com.utn.phones.dto.CityDto;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.City;
import com.utn.phones.model.Province;

import java.util.List;

public interface ICityService {
    City getById(Integer id) throws ResourceNotFoundException;
    List<CityDto> getByProvince(Province province) throws ResourceNotFoundException;
}
