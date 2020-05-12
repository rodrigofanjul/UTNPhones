package com.utn.UTNPhones.Services;

import com.utn.UTNPhones.Models.Province;
import com.utn.UTNPhones.Repositories.IProvinceRepository;
import com.utn.UTNPhones.Services.Interfaces.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProvinceService implements IProvinceService {

    private final IProvinceRepository provinceRepository;

    @Autowired
    public ProvinceService(IProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    public Province getById(Integer id) {
        return provinceRepository.findById(id).orElse(null);
    }
}
