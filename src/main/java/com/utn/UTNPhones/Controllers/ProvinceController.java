package com.utn.UTNPhones.Controllers;

import com.utn.UTNPhones.Services.Interfaces.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/province")
public class ProvinceController {

    private final IProvinceService provinceService;

    @Autowired
    public ProvinceController(IProvinceService provinceService) {
        this.provinceService = provinceService;
    }
}
