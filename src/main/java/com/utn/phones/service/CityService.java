package com.utn.phones.service;

import com.utn.phones.dto.CityDto;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.City;
import com.utn.phones.model.Province;
import com.utn.phones.repository.ICityRepository;
import com.utn.phones.service.interfaces.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService implements ICityService {

    private final ICityRepository cityRepository;

    @Autowired
    public CityService(ICityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City getById(Integer id) throws ResourceNotFoundException {
        return cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource City not found with (id:%d)",id));
    }

    public List<CityDto> getByProvince(Province province) throws ResourceNotFoundException {
        return Optional.ofNullable(cityRepository.findByProvince(province))
                .orElseThrow(() -> new ResourceNotFoundException("Resource User not found with Province (id:%d)", province.getId()));
    }
}
