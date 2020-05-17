package com.utn.phones.controller;

import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.City;
import com.utn.phones.model.Province;
import com.utn.phones.model.Phoneline;
import com.utn.phones.model.User;
import static com.utn.phones.model.Phoneline.Status.ACTIVE;
import static com.utn.phones.model.Phoneline.Type.MOBILE;
import static com.utn.phones.model.User.Role.EMPLOYEE;
import com.utn.phones.service.PhonelineService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class PhonelineControllerTest {

    PhonelineService phonelineService;
    PhonelineController phonelineController;
    User testUser;
    Phoneline testPhoneline;
    List<Phoneline> testPhonelines;

    @Before
    public void setUp() {
        phonelineService = mock(PhonelineService.class);
        phonelineController = new PhonelineController(phonelineService);
        testUser = new User(1,new City(1,new Province(1,"Buenos Aires"),"Mar del Plata",223),"nombre","apellido",123,"123", EMPLOYEE);
        testPhoneline = new Phoneline(1l,testUser,testUser.getCity(),MOBILE,ACTIVE);
        testPhonelines = new ArrayList<Phoneline>();
        testPhonelines.add(testPhoneline);
    }

    @Test
    public void testGetPhonelinesOk() throws ResourceNotFoundException {
        try {
            when(phonelineService.getAll()).thenReturn(testPhonelines);
            List<Phoneline> phonelines = phonelineController.getPhonelines();
            assertEquals(1, phonelines.size());
            assertEquals(Long.valueOf(1), phonelines.get(0).getId());
            verify(phonelineService, times(1)).getAll();
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetPhonelinesNotFound() throws ResourceNotFoundException {
        when(phonelineService.getAll()).thenThrow(new ResourceNotFoundException());
        List<Phoneline> phonelines = phonelineController.getPhonelines();
    }

    @Test
    public void testNewPhonelineOk() throws ResourceNotFoundException, ResourceAlreadyExistsException {
        try {
            when(phonelineService.newPhoneline(testPhoneline)).thenReturn(testPhoneline);
            Phoneline phoneline = phonelineController.newPhoneline(testPhoneline);
            assertEquals(Long.valueOf(1), phoneline.getId());
            verify(phonelineService, times(1)).newPhoneline(testPhoneline);
        }
        catch (ResourceNotFoundException | ResourceAlreadyExistsException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testNewPhonelineNotFound() throws ResourceNotFoundException, ResourceAlreadyExistsException {
        when(phonelineService.newPhoneline(testPhoneline)).thenThrow(new ResourceNotFoundException());
        Phoneline phoneline = phonelineController.newPhoneline(testPhoneline);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void testNewPhonelineAlreadyExists() throws ResourceAlreadyExistsException, ResourceNotFoundException {
        when(phonelineService.newPhoneline(testPhoneline)).thenThrow(new ResourceAlreadyExistsException());
        Phoneline phoneline = phonelineController.newPhoneline(testPhoneline);
    }
}
