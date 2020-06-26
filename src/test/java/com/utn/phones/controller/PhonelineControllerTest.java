package com.utn.phones.controller;

import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.City;
import com.utn.phones.model.Phoneline;
import com.utn.phones.model.Province;
import com.utn.phones.model.User;
import com.utn.phones.service.PhonelineService;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static com.utn.phones.model.Phoneline.Status.ACTIVE;
import static com.utn.phones.model.Phoneline.Type.MOBILE;
import static com.utn.phones.model.User.Role.EMPLOYEE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class PhonelineControllerTest {

    PhonelineService phonelineService;
    PhonelineController phonelineController;

    City testCity;
    User testUser;
    Phoneline testPhoneline;
    List<Phoneline> testPhonelines;

    @Before
    public void setUp() {
        phonelineService = mock(PhonelineService.class);
        phonelineController = new PhonelineController(phonelineService);
        testCity = new City(1,new Province(1,"Buenos Aires"),"Mar del Plata",223);
        testUser = new User(1,testCity,"nombre","apellido",123,"123", EMPLOYEE);
        testPhoneline = new Phoneline();
        testPhoneline = new Phoneline(1L,testUser,testCity,MOBILE,ACTIVE);
        testPhonelines = Collections.singletonList(testPhoneline);
    }

    @Test
    public void testGetPhonelinesOk() {
        when(phonelineService.getAll()).thenReturn(testPhonelines);
        List<Phoneline> phonelines = phonelineController.getPhonelines();
        assertEquals(1, phonelines.size());
        assertEquals(Long.valueOf(1), phonelines.get(0).getId());
        verify(phonelineService, times(1)).getAll();
    }

    @Test
    public void testGetPhonelineByIdOk() throws ResourceNotFoundException {
        when(phonelineService.getById(1L)).thenReturn(testPhoneline);
        Phoneline phoneline = phonelineController.getPhonelineById(1L);
        assertEquals(Long.valueOf(1), phoneline.getId());
        assertEquals(testUser, phoneline.getUser());
        assertEquals(testCity, phoneline.getCity());
        assertEquals(MOBILE, phoneline.getType());
        assertEquals(ACTIVE, phoneline.getStatus());
        verify(phonelineService, times(1)).getById(1L);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetPhonelineByIdNotFound() throws ResourceNotFoundException {
        when(phonelineService.getById(1L)).thenThrow(new ResourceNotFoundException());
        phonelineController.getPhonelineById(1L);
    }

    @Test
    public void testNewPhonelineOk() throws ResourceAlreadyExistsException {
        when(phonelineService.newPhoneline(testPhoneline)).thenReturn(testPhoneline);
        Phoneline phoneline = phonelineController.newPhoneline(testPhoneline);
        assertEquals(Long.valueOf(1), phoneline.getId());
        verify(phonelineService, times(1)).newPhoneline(testPhoneline);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void testNewPhonelineAlreadyExists() throws ResourceAlreadyExistsException {
        when(phonelineService.newPhoneline(testPhoneline)).thenThrow(new ResourceAlreadyExistsException());
        phonelineController.newPhoneline(testPhoneline);
    }
}
