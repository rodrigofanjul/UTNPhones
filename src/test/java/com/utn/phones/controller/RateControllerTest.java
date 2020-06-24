package com.utn.phones.controller;

import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.City;
import com.utn.phones.model.Province;
import com.utn.phones.model.Rate;
import com.utn.phones.service.RateService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class RateControllerTest {

    RateService rateService;
    RateController rateController;

    City testCity;
    Rate testRate;
    List<Rate> testRates;

    @Before
    public void setUp() {
        rateService = mock(RateService.class);
        rateController = new RateController(rateService);

        testCity = new City(1,new Province(1,"Buenos Aires"),"Mar del Plata",223);
        testRate = new Rate();
        testRate = new Rate(1,testCity,testCity,1.0f);
        testRates = Arrays.asList(testRate);
    }

    @Test
    public void testGetRatesOk() throws ResourceNotFoundException {
        try {
            when(rateService.getAll()).thenReturn(testRates);
            List<Rate> rates = rateController.getRates();
            assertEquals(1, rates.size());
            assertEquals(Integer.valueOf(1), rates.get(0).getId());
            assertEquals(Float.valueOf(1.0f), rates.get(0).getRate());
            verify(rateService, times(1)).getAll();
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetRatesNotFound() throws ResourceNotFoundException {
        when(rateService.getAll()).thenThrow(new ResourceNotFoundException());
        List<Rate> rates = rateController.getRates();
    }
}
