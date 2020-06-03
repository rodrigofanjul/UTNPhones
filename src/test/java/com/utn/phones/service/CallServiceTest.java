package com.utn.phones.service;

import com.utn.phones.dto.MostCalledDto;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.*;
import com.utn.phones.repository.ICallRepository;
import com.utn.phones.service.interfaces.IPhonelineService;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.utn.phones.model.Phoneline.Status.ACTIVE;
import static com.utn.phones.model.Phoneline.Type.MOBILE;
import static com.utn.phones.model.User.Role.EMPLOYEE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CallServiceTest {

    ICallRepository callRepository;
    IPhonelineService phonelineService;
    CallService callService;
    Date testDate;
    Invoice testInvoice;
    Phoneline testPhonelineOrigin;
    Phoneline testPhonelineDestination;
    User testUser;
    Call testCall;
    List<Call> testCalls;

    @Before
    public void setUp() {
        callRepository = mock(ICallRepository.class);
        phonelineService = mock(IPhonelineService.class);
        callService = new CallService(callRepository,phonelineService);

        testDate = new Date();
        testUser = new User(1,new City(1,new Province(1,"Buenos Aires"),"Mar del Plata",223),"nombre","apellido",123,"123", EMPLOYEE);
        testPhonelineOrigin = new Phoneline(1l,testUser,testUser.getCity(),MOBILE,ACTIVE);
        testPhonelineDestination = new Phoneline(1l,testUser,testUser.getCity(),MOBILE,ACTIVE);
        testInvoice = new Invoice(1,testPhonelineOrigin,1,1f,1.21f,testDate,false,testDate);
        testCall = new Call(1,testInvoice,testPhonelineOrigin,testPhonelineDestination,testDate,1.0f,10,1.0f);
        testCalls = Arrays.asList(testCall);
    }

    @Test
    public void testGetAllOk() throws ResourceNotFoundException {
        when(callRepository.findAll()).thenReturn(testCalls);
        List<Call> calls = callService.getAll();
        assertEquals(1, calls.size());
        assertEquals(Integer.valueOf(1), calls.get(0).getId());
        verify(callRepository, times(1)).findAll();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetAllNotFound() throws ResourceNotFoundException {
        when(callRepository.findAll()).thenReturn(null);
        List<Call> calls = callService.getAll();
    }

    @Test
    public void testGetByUserOk() throws ResourceNotFoundException {
        when(callRepository.findByOriginIn(phonelineService.getByUser(testUser))).thenReturn(testCalls);
        List<Call> calls = callService.getByUser(testUser);
        assertEquals(Integer.valueOf(1), calls.get(0).getId());
        verify(callRepository, times(1)).findByOriginIn(phonelineService.getByUser(testUser));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetByUserNotFound() throws ResourceNotFoundException {
        when(callRepository.findByOriginIn(phonelineService.getByUser(testUser))).thenReturn(null);
        List<Call> calls = callService.getByUser(testUser);
    }

    @Test
    public void testGetByUserBetweenOk() throws ResourceNotFoundException {
        when(callRepository.findByOriginInAndDateBetween(phonelineService.getByUser(testUser),testDate,testDate)).thenReturn(testCalls);
        List<Call> calls = callService.getByUserBetween(testUser,testDate,testDate);
        assertEquals(Integer.valueOf(1), calls.get(0).getId());
        verify(callRepository, times(1)).findByOriginInAndDateBetween(phonelineService.getByUser(testUser),testDate,testDate);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetByUserBetweenNotFound() throws ResourceNotFoundException {
        when(callRepository.findByOriginInAndDateBetween(phonelineService.getByUser(testUser),testDate,testDate)).thenReturn(null);
        List<Call> calls = callService.getByUserBetween(testUser,testDate,testDate);
    }

//    @Test
//    public void testGetByUserMostCalledOk() throws ResourceNotFoundException {
//        when(callRepository.findTopByOriginInOrderByDestinationCountDesc(phonelineService.getByUser(testUser))).thenReturn(testCalls);
//        List<MostCalledDto> calls = callService.getByUserMostCalled(testUser);
//        assertEquals(Integer.valueOf(1), calls.get(0).getId());
//        verify(callRepository, times(1)).findTopByOriginInOrderByDestinationCountDesc(phonelineService.getByUser(testUser));
//    }
//
//    @Test(expected = ResourceNotFoundException.class)
//    public void testGetByUserMostCalledNotFound() throws ResourceNotFoundException {
//        when(callRepository.findTopByOriginInOrderByDestinationCountDesc(phonelineService.getByUser(testUser))).thenReturn(null);
//        List<MostCalledDto> calls = callService.getByUserMostCalled(testUser);
//    }

    @Test
    public void testNewCallOk() throws ResourceNotFoundException {
        when(callRepository.save(testCall)).thenReturn(testCall);
        Call call = callService.newCall(testCall);
        assertEquals(Integer.valueOf(1), call.getId());
        verify(phonelineService,times(2)).getById(1l);
        verify(callRepository, times(1)).save(testCall);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testNewCallNotFound() throws ResourceNotFoundException {
        when(phonelineService.getById(testCall.getOrigin().getId())).thenThrow(new ResourceNotFoundException());
        Call call = callService.newCall(testCall);
    }
}
