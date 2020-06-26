package com.utn.phones.service;

import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.City;
import com.utn.phones.model.Phoneline;
import com.utn.phones.model.Province;
import com.utn.phones.model.User;
import com.utn.phones.repository.IPhonelineRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.utn.phones.model.Phoneline.Status.ACTIVE;
import static com.utn.phones.model.Phoneline.Type.MOBILE;
import static com.utn.phones.model.User.Role.EMPLOYEE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class PhonelineServiceTest {

    IPhonelineRepository phonelineRepository;
    PhonelineService phonelineService;

    Date testDate;
    Phoneline testPhoneline;
    User testUser;
    List<Phoneline> testPhonelines;

    @Before
    public void setUp() {
        phonelineRepository = mock(IPhonelineRepository.class);
        phonelineService = new PhonelineService(phonelineRepository);

        testDate = new Date();
        testUser = new User(1,new City(1,new Province(1,"Buenos Aires"),"Mar del Plata",223),"nombre","apellido",123,"123", EMPLOYEE);
        testPhoneline = new Phoneline(1L,testUser,testUser.getCity(),MOBILE,ACTIVE);
        testPhonelines = Collections.singletonList(testPhoneline);
    }

    @Test
    public void testGetAllOk() {
        when(phonelineRepository.findAll()).thenReturn(testPhonelines);
        List<Phoneline> phonelines = phonelineService.getAll();
        assertEquals(1, phonelines.size());
        assertEquals(Long.valueOf(1), phonelines.get(0).getId());
        verify(phonelineRepository, times(1)).findAll();
    }

    @Test
    public void testGetByIdOk() throws ResourceNotFoundException {
        when(phonelineRepository.findById(1L)).thenReturn(testPhoneline);
        Phoneline phoneline = phonelineService.getById(1L);
        assertEquals(Long.valueOf(1), phoneline.getId());
        verify(phonelineRepository, times(1)).findById(1L);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetByIdNotFound() throws ResourceNotFoundException {
        when(phonelineRepository.findById(1)).thenReturn(Optional.empty());
        phonelineService.getById(1L);
    }

    @Test
    public void testGetByUserOk() throws ResourceNotFoundException {
        when(phonelineRepository.findByUser(testUser)).thenReturn(testPhonelines);
        List<Phoneline> phonelines = phonelineService.getByUser(testUser);
        assertEquals(1, phonelines.size());
        assertEquals(Long.valueOf(1), phonelines.get(0).getId());
        verify(phonelineRepository, times(1)).findByUser(testUser);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetByUserNotFound() throws ResourceNotFoundException {
        when(phonelineRepository.findByUser(testUser)).thenReturn(null);
        phonelineService.getByUser(testUser);
    }

    @Test
    public void testNewPhonelineOk() throws ResourceAlreadyExistsException {
        when(phonelineRepository.save(testPhoneline)).thenReturn(testPhoneline);
        Phoneline phoneline = phonelineService.newPhoneline(testPhoneline);
        assertEquals(Long.valueOf(1), phoneline.getId());
        verify(phonelineRepository, times(1)).findById(testPhoneline.getId());
        verify(phonelineRepository, times(1)).save(testPhoneline);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void testNewPhonelineAlreadyExists() throws ResourceAlreadyExistsException {
        when(phonelineRepository.findById(testPhoneline.getId())).thenReturn(testPhoneline);
        phonelineService.newPhoneline(testPhoneline);
    }
}
