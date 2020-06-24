package com.utn.phones.controller.web;

import com.utn.phones.exception.ApiErrorException;
import com.utn.phones.service.ProvinceComponent;
import org.springframework.beans.factory.annotation.Autowired;
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

//    private final ProvinceController provinceController;
//    private final CityController cityController;
    private final ProvinceComponent provinceComponent;

    @Autowired
    public ProvinceWebController(ProvinceComponent provinceComponent) {
//        this.provinceController = provinceController;
//        this.cityController = cityController;
        this.provinceComponent = provinceComponent;
    }

//    @IsEmployee
//    @GetMapping("/{id}/cities")
//    public ResponseEntity<List<CityDto>> getCitiesByProvince(@Validated @PathVariable @Min(1) int id) throws ResourceNotFoundException {
//        List<CityDto> cities = cityController.getCitiesByProvince(provinceController.getProvinceById(id));
//        return (cities.size() > 0) ? ResponseEntity.ok(cities) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }

    // Mi nueva "api" se va a comunicar con la primera por medio de RestTemplate para obtener los datos
    // Por lo tanto debo comentar el método anterior.

    @IsEmployee
    @GetMapping("/{id}/cities")
    public ResponseEntity<List> getCitiesByProvince(@Validated @PathVariable @Min(1) int id) throws ApiErrorException {
        return provinceComponent.getCitiesByProvince(id);
    }
}
