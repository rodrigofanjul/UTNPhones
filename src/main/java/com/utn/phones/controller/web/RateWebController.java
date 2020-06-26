package com.utn.phones.controller.web;

import com.utn.phones.controller.RateController;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Rate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.utn.phones.security.SecurityConfig.*;

@RestController
@RequestMapping("/api/rates")
public class RateWebController {

    private final RateController rateController;

    @Autowired
    public RateWebController(RateController rateController) {
        this.rateController = rateController;
    }

    @IsEmployee
    @GetMapping
    public ResponseEntity<List<Rate>> getRates() {
        List<Rate> rates = rateController.getRates();
        return (rates.size() > 0) ? ResponseEntity.ok(rates) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
