package com.utn.UTNPhones.Services;

import com.utn.UTNPhones.Exceptions.ResourceNotFoundException;
import com.utn.UTNPhones.Models.Rate;
import com.utn.UTNPhones.Repositories.IRateRepository;
import com.utn.UTNPhones.Services.Interfaces.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RateService implements IRateService {

    private final IRateRepository rateRepository;

    @Autowired
    public RateService(IRateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    public List<Rate> getAll() throws ResourceNotFoundException {
        return Optional.ofNullable(rateRepository.findAll())
            .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource Rate not found")));
    }
}
