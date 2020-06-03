package com.utn.phones.service;

import com.sun.org.apache.regexp.internal.RE;
import com.utn.phones.dto.CityDto;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.City;
import com.utn.phones.model.Province;
import com.utn.phones.model.User;
import com.utn.phones.repository.ICityRepository;
import com.utn.phones.repository.IProvinceRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CityServiceTest {

    ICityRepository cityRepository;
    CityService cityService;

    Province testProvince;
    City testCity;
    CityDto testCityDto;
    List<City> testCities;
    List<CityDto> testCitiesDto;

    @Before
    public void setUp() {
        cityRepository = mock(ICityRepository.class);
        cityService = new CityService(cityRepository);

        testProvince = new Province(1,"Buenos Aires");
        testCity = new City(1, testProvince,"Mar del Plata",223);
        testCityDto = new CityDto("Mar del Plata", 223);
        testCities = Arrays.asList(testCity);
        testCitiesDto = Arrays.asList(testCityDto);
    }

    @Test
    public void testGetByIdOk() throws ResourceNotFoundException {
        when(cityRepository.findById(1)).thenReturn(Optional.ofNullable(testCity));
        City city = cityService.getById(1);
        assertEquals(Integer.valueOf(1), city.getId());
        verify(cityRepository, times(1)).findById(1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetByIdNotFound() throws ResourceNotFoundException {
        when(cityRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        City city = cityService.getById(1);
    }

    @Test
    public void testGetByProvinceOk() throws ResourceNotFoundException {
        when(cityRepository.findByProvince(testProvince)).thenReturn(testCitiesDto);
        List<CityDto> cities = cityService.getByProvince(testProvince);
        assertEquals(1, cities.size());
        assertEquals("Mar del Plata", cities.get(0).getName());
        assertEquals(Integer.valueOf(223), cities.get(0).getPrefix());
        verify(cityRepository, times(1)).findByProvince(testProvince);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetByProvinceNotFound() throws ResourceNotFoundException {
        when(cityRepository.findByProvince(testProvince)).thenReturn(null);
        List<CityDto> cities = cityService.getByProvince(testProvince);
    }
}
