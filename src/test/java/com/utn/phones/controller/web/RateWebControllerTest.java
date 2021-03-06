package com.utn.phones.controller.web;

import com.utn.phones.controller.RateController;
import com.utn.phones.model.City;
import com.utn.phones.model.Invoice;
import com.utn.phones.model.Province;
import com.utn.phones.model.Rate;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
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
        testRates = Collections.singletonList(testRate);
    }

    @Test
    public void testGetRatesOk() {
        when(rateController.getRates()).thenReturn(testRates);

        ResponseEntity<List<Rate>> response = rateWebController.getRates();

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(Integer.valueOf(1), response.getBody().get(0).getId());
        assertEquals(Float.valueOf(1.0f), response.getBody().get(0).getRate());
        verify(rateController, times(1)).getRates();
    }

    @Test
    public void testGetInvoicesNoContent() {
        when(rateController.getRates()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Rate>> response = rateWebController.getRates();

        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
        verify(rateController, times(1)).getRates();
    }
}
