package com.utn.UTNPhones.Controllers;

import com.utn.UTNPhones.Exceptions.NotFoundException;
import com.utn.UTNPhones.Models.Rate;
import com.utn.UTNPhones.Services.Interfaces.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rates")
public class RateController {

    private final IRateService rateService;

    @Autowired
    public RateController(IRateService rateService) {
        this.rateService = rateService;
    }

    @GetMapping("")
    public List<Rate> getAll() throws NotFoundException {
        List<Rate> rate = rateService.getAll();
        return rate;
    }
}
