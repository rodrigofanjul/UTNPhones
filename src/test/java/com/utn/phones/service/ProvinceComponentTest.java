package com.utn.phones.service;

import com.utn.phones.dto.CityDto;
import com.utn.phones.exception.ApiErrorException;
import com.utn.phones.model.City;
import com.utn.phones.model.Province;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class ProvinceComponentTest {

    RestTemplate restTemplate;
    ProvinceComponent provinceComponent;

    Province testProvince;
    City testCity;
    CityDto testCityDto;
    List<City> testCities;
    List<CityDto> testCitiesDto;

    @Before
    public void setUp() {
        restTemplate = mock(RestTemplate.class);
        provinceComponent = new ProvinceComponent();

        testProvince = new Province(1,"Buenos Aires");
        testCity = new City(1, testProvince,"Mar del Plata",223);
        testCityDto = new CityDto("Mar del Plata", 223);
        testCities = Arrays.asList(testCity);
        testCitiesDto = Arrays.asList(testCityDto);
    }

    @Test(expected = ApiErrorException.class)
    public void testGetCitiesByProvinceApiError() {
        when(restTemplate.getForEntity("http://localhost:8080/api/provinces/1/cities", List.class)).thenThrow(new ApiErrorException());
        ResponseEntity<List> response = provinceComponent.getCitiesByProvince(1);
    }
}
