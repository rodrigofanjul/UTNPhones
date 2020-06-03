package com.utn.phones.controller;

import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Province;
import com.utn.phones.service.interfaces.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProvinceController {

    private final IProvinceService provinceService;

    @Autowired
    public ProvinceController(IProvinceService provinceService) { this.provinceService = provinceService; }

    public Province getProvinceById(int id) throws ResourceNotFoundException {
        return provinceService.getById(id);
    }
}
