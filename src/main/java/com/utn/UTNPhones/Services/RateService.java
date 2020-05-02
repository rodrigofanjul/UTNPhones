package com.utn.UTNPhones.Services;

import com.utn.UTNPhones.Repositories.IRateRepository;
import com.utn.UTNPhones.Services.Interfaces.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateService implements IRateService {

    private final IRateRepository rateRepository;

    @Autowired
    public RateService(IRateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }
}
