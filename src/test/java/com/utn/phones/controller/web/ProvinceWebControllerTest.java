package com.utn.phones.controller.web;

import com.utn.phones.controller.CityController;
import com.utn.phones.controller.ProvinceController;
import com.utn.phones.dto.CityDto;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class ProvinceWebControllerTest {
    ProvinceController provinceController;
    CityController cityController;
    ProvinceWebController provinceWebController;

    Province testProvince;
    City testCity;
    CityDto testCityDto;
    List<City> testCities;
    List<CityDto> testCitiesDto;

    @Before
    public void setUp() {
        provinceController = mock(ProvinceController.class);
        cityController = mock(CityController.class);
        provinceWebController = new ProvinceWebController(provinceController,cityController);

        testProvince = new Province(1,"Buenos Aires");
        testCity = new City(1, testProvince,"Mar del Plata",223);
        testCityDto = new CityDto("Mar del Plata", 223);
        testCities = Arrays.asList(testCity);
        testCitiesDto = Arrays.asList(testCityDto);
    }
}
