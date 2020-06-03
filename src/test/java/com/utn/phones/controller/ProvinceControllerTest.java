package com.utn.phones.controller;

import com.utn.phones.dto.CityDto;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.City;
import com.utn.phones.model.Province;
import com.utn.phones.model.Province;
import com.utn.phones.service.CityService;
import com.utn.phones.service.ProvinceService;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class ProvinceControllerTest {
    ProvinceService provinceService;
    ProvinceController provinceController;

    Province testProvince;

    @Before
    public void setUp() {
        provinceService = mock(ProvinceService.class);
        provinceController = new ProvinceController(provinceService);

        testProvince = new Province(1,"Buenos Aires");
    }

    @Test
    public void testGetProvinceByIdOk() throws ResourceNotFoundException {
        try {
            when(provinceService.getById(1)).thenReturn(testProvince);
            Province province = provinceController.getProvinceById(1);
            assertEquals(Integer.valueOf(1), province.getId());
            assertEquals("Buenos Aires", province.getName());
            verify(provinceService, times(1)).getById(1);
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetProvinceByIdNotFound() throws ResourceNotFoundException {
        when(provinceService.getById(1)).thenThrow(new ResourceNotFoundException());
        Province province = provinceController.getProvinceById(1);
    }
}
