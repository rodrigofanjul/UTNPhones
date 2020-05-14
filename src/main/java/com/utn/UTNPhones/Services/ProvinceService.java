package com.utn.UTNPhones.Services;

import com.utn.UTNPhones.Exceptions.ResourceNotFoundException;
import com.utn.UTNPhones.Models.Province;
import com.utn.UTNPhones.Repositories.IProvinceRepository;
import com.utn.UTNPhones.Services.Interfaces.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProvinceService implements IProvinceService {

    private final IProvinceRepository provinceRepository;

    @Autowired
    public ProvinceService(IProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    public Province getById(Integer id) throws ResourceNotFoundException {
        return provinceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource Province not found with (id:%d)",id)));
    }
}
