package com.utn.phones.controller;

import com.utn.phones.model.Rate;
import com.utn.phones.service.interfaces.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class RateController {

    private final IRateService rateService;

    @Autowired
    public RateController(IRateService rateService) {
        this.rateService = rateService;
    }

    public List<Rate> getRates() {
        return rateService.getAll();
    }
}
