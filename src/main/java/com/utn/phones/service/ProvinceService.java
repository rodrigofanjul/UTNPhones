package com.utn.phones.service;

import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Province;
import com.utn.phones.repository.IProvinceRepository;
import com.utn.phones.service.interfaces.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
