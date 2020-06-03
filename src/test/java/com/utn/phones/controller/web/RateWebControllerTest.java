package com.utn.phones.controller.web;

import com.utn.phones.controller.RateController;
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

public class RateWebControllerTest {

    RateController rateController;
    RateWebController rateWebController;

    City testCity;
    Rate testRate;
    List<Rate> testRates;

    @Before
    public void setUp() {
        rateController = mock(RateController.class);
        rateWebController = new RateWebController(rateController);

        testCity = new City(1,new Province(1,"Buenos Aires"),"Mar del Plata",223);
        testRate = new Rate(1,testCity,testCity,1.0f);
        testRates = Arrays.asList(testRate);
    }

    @Test
    public void testGetCallsOk() throws ResourceNotFoundException {
        try {
            when(rateController.getRates()).thenReturn(testRates);
            ResponseEntity<List<Rate>> response = rateWebController.getRates();
            assertEquals(HttpStatus.OK,response.getStatusCode());
            assertEquals(1, response.getBody().size());
            assertEquals(Integer.valueOf(1), response.getBody().get(0).getId());
            assertEquals(Float.valueOf(1.0f), response.getBody().get(0).getRate());
            verify(rateController, times(1)).getRates();
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetCallsNotFound() throws ResourceNotFoundException {
        when(rateController.getRates()).thenThrow(new ResourceNotFoundException());
        ResponseEntity<List<Rate>> response = rateWebController.getRates();
    }
}
