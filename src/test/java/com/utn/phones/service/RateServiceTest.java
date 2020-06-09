package com.utn.phones.service;

import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.City;
import com.utn.phones.model.Province;
import com.utn.phones.model.Rate;
import com.utn.phones.repository.IRateRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RateServiceTest {

    IRateRepository rateRepository;
    RateService rateService;
    City testCity;
    Rate testRate;
    List<Rate> testRates;

    @Before
    public void setUp() {
        rateRepository = mock(IRateRepository.class);
        rateService = new RateService(rateRepository);

        testCity = new City(1,new Province(1,"Buenos Aires"),"Mar del Plata",223);
        testRate = new Rate(1,testCity,testCity,1.0f);
        testRates = Arrays.asList(testRate);
    }

    @Test
    public void testGetAllOk() throws ResourceNotFoundException {
        when(rateRepository.findAll()).thenReturn(testRates);

        List<Rate> rates = rateService.getAll();

        assertEquals(1, rates.size());
        assertEquals(testRates, rates);
        assertEquals(Integer.valueOf(1), rates.get(0).getId());
        assertEquals(testCity, rates.get(0).getOrigin());
        assertEquals(testCity, rates.get(0).getDestination());
        assertEquals(Float.valueOf(1.0f), rates.get(0).getRate());
        verify(rateRepository, times(1)).findAll();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetAllNotFound() throws ResourceNotFoundException {
        when(rateRepository.findAll()).thenReturn(null);
        List<Rate> rates = rateService.getAll();
    }
}
