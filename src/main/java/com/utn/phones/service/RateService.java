package com.utn.phones.service;

import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Rate;
import com.utn.phones.repository.IRateRepository;
import com.utn.phones.service.interfaces.IRateService;
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
            .orElseThrow(() -> new ResourceNotFoundException());
    }
}
