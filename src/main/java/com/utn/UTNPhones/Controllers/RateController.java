package com.utn.UTNPhones.Controllers;

import com.utn.UTNPhones.Exceptions.ResourceNotFoundException;
import com.utn.UTNPhones.Models.Rate;
import com.utn.UTNPhones.Services.Interfaces.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @GetMapping("")
    public ResponseEntity<Object> getAll() throws ResourceNotFoundException {
        List<Rate> rates = rateService.getAll();
        return ResponseEntity.ok(rates);
    }
}
