package com.utn.UTNPhones.Services;

import com.utn.UTNPhones.Repositories.IProvinceRepository;
import com.utn.UTNPhones.Services.interfaces.IProvineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProvinceService implements IProvineService {

    private final IProvinceRepository provinceRepository;

    @Autowired
    public ProvinceService(IProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }
}
