package com.utn.phones.controller;

import com.utn.phones.dto.CityDto;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.*;
import com.utn.phones.service.CallService;
import com.utn.phones.service.CityService;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.utn.phones.model.Phoneline.Status.ACTIVE;
import static com.utn.phones.model.Phoneline.Type.MOBILE;
import static com.utn.phones.model.User.Role.EMPLOYEE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CityControllerTest {

    CityService cityService;
    CityController cityController;

    Province testProvince;
    City testCity;
    CityDto testCityDto;
    List<City> testCities;
    List<CityDto> testCitiesDto;

    @Before
    public void setUp() {
        cityService = mock(CityService.class);
        cityController = new CityController(cityService);

        testProvince = new Province(1,"Buenos Aires");
        testCity = new City(1, testProvince,"Mar del Plata",223);
        testCityDto = new CityDto("Mar del Plata", 223);
        testCities = Arrays.asList(testCity);
        testCitiesDto = Arrays.asList(testCityDto);
    }

    @Test
    public void testGetByProvinceOk() throws ResourceNotFoundException {
        when(cityService.getByProvince(testProvince)).thenReturn(testCitiesDto);
        List<CityDto> cities = cityController.getCitiesByProvince(testProvince);
        assertEquals(1, cities.size());
        assertEquals("Mar del Plata", cities.get(0).getName());
        assertEquals(Integer.valueOf(223), cities.get(0).getPrefix());
        verify(cityService, times(1)).getByProvince(testProvince);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetByProvinceNotFound() throws ResourceNotFoundException {
        when(cityService.getByProvince(testProvince)).thenThrow(new ResourceNotFoundException());
        List<CityDto> cities = cityService.getByProvince(testProvince);
    }
}
