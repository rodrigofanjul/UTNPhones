package com.utn.phones.service;

import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.Province;
import com.utn.phones.repository.IProvinceRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ProvinceServiceTest {

    IProvinceRepository provinceRepository;
    ProvinceService provinceService;

    Province testProvince;

    @Before
    public void setUp() {
        provinceRepository = mock(IProvinceRepository.class);
        provinceService = new ProvinceService(provinceRepository);

        testProvince = new Province();
        testProvince = new Province(1,"Buenos Aires");
    }

    @Test
    public void testGetByIdOk() throws ResourceNotFoundException {
        when(provinceRepository.findById(1)).thenReturn(Optional.ofNullable(testProvince));

        Province province = provinceService.getById(1);

        assertEquals(Integer.valueOf(1), province.getId());
        assertEquals("Buenos Aires", province.getName());
        verify(provinceRepository, times(1)).findById(1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetByIdNotFound() throws ResourceNotFoundException {
        when(provinceRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        Province province = provinceService.getById(1);
    }
}
