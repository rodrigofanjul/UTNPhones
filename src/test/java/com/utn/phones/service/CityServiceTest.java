package com.utn.phones.service;

import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.City;
import com.utn.phones.model.Province;
import com.utn.phones.repository.ICityRepository;
import com.utn.phones.repository.IProvinceRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CityServiceTest {

    ICityRepository cityRepository;
    CityService cityService;

    Province testProvince;
    City testCity;

    @Before
    public void setUp() {
        cityRepository = mock(ICityRepository.class);
        cityService = new CityService(cityRepository);

        testProvince = new Province(1,"Buenos Aires");
        testCity = new City();
        testCity = new City(1,testProvince,"Mar del Plata",223);
    }

    @Test
    public void testGetByIdOk() throws ResourceNotFoundException {
        when(cityRepository.findById(1)).thenReturn(Optional.ofNullable(testCity));

        City city = cityService.getById(1);

        assertEquals(Integer.valueOf(1), city.getId());
        assertEquals(testProvince, city.getProvince());
        assertEquals("Mar del Plata", city.getName());
        assertEquals(Integer.valueOf(223), city.getPrefix());
        verify(cityRepository, times(1)).findById(1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetByIdNotFound() throws ResourceNotFoundException {
        when(cityRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        City city = cityService.getById(1);
    }
}
