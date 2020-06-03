package com.utn.phones.controller;

import com.utn.phones.dto.CityDto;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Province;
import com.utn.phones.service.interfaces.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CityController {

    private final ICityService cityService;

    @Autowired
    public CityController(ICityService cityService) { this.cityService = cityService; }

    public List<CityDto> getCitiesByProvince(Province province) throws ResourceNotFoundException {
        return cityService.getByProvince(province);
    }
}
