package com.utn.phones.controller.web;

import com.utn.phones.controller.CityController;
import com.utn.phones.controller.ProvinceController;
import com.utn.phones.dto.CityDto;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.City;
import com.utn.phones.model.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

import static com.utn.phones.security.SecurityConfig.*;

import java.util.List;

@RestController
@RequestMapping("/api/provinces")
public class ProvinceWebController {

    private final ProvinceController provinceController;
    private final CityController cityController;

    @Autowired
    public ProvinceWebController(ProvinceController provinceController, CityController cityController) {
        this.provinceController = provinceController;
        this.cityController = cityController;
    }

    @IsEmployee
    @GetMapping("/{id}/cities")
    public ResponseEntity<List<CityDto>> getCitiesByProvince(@Validated @PathVariable @Min(1) int id) throws ResourceNotFoundException {
        List<CityDto> cities = cityController.getCitiesByProvince(provinceController.getProvinceById(id));
        return (cities.size() > 0) ? ResponseEntity.ok(cities) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
