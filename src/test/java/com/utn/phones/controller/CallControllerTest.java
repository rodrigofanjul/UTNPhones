package com.utn.phones.controller;

import com.utn.phones.dto.MostCalledDto;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.*;
import com.utn.phones.service.CallService;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.utn.phones.model.Phoneline.Status.ACTIVE;
import static com.utn.phones.model.Phoneline.Type.MOBILE;
import static com.utn.phones.model.User.Role.EMPLOYEE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CallControllerTest {

    CallService callService;
    CallController callController;
    Date testDate;
    City testCity;
    Phoneline testPhoneline;
    Invoice testInvoice;
    User testUser;
    Call testCall;
    List<Call> testCalls;
    MostCalledDto testMostCalledDto;
    List<MostCalledDto> testMostCalledDtos;

    @Before
    public void setUp() {
        callService = mock(CallService.class);
        callController = new CallController(callService);

        testDate = new Date();
        testCity = new City(1,new Province(1,"Buenos Aires"),"Mar del Plata",223);
        testUser = new User(1, testCity,"nombre","apellido",123,"123", EMPLOYEE);
        testPhoneline = new Phoneline(1L,testUser,testUser.getCity(),MOBILE,ACTIVE);
        testCall = new Call();
        testCall = new Call(1,null,testPhoneline,testPhoneline,testDate,1.0f,10,1.0f);
        testInvoice = new Invoice(1,new Phoneline(1L,testUser,testUser.getCity(),MOBILE,ACTIVE),1,1f,1.21f,testDate,false,testDate);
        testCalls = Collections.singletonList(testCall);
        testMostCalledDto = new MostCalledDto(1L, 1L, 1L);
        testMostCalledDtos = Collections.singletonList(testMostCalledDto);
    }

    @Test
    public void testGetCallsOk() {
        when(callService.getAll()).thenReturn(testCalls);

        List<Call> calls = callController.getCalls();

        assertEquals(1, calls.size());
        assertEquals(Integer.valueOf(1), calls.get(0).getId());
        assertEquals(testCity, calls.get(0).getOrigin().getUser().getCity());
        verify(callService, times(1)).getAll();
    }

    @Test
    public void testGetCallsByUserOk() throws ResourceNotFoundException {
        when(callService.getByUser(testUser)).thenReturn(testCalls);

        List<Call> calls = callController.getCallsByUser(testUser);

        assertEquals(1, calls.size());
        assertEquals(Integer.valueOf(1), calls.get(0).getId());
        verify(callService, times(1)).getByUser(testUser);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetCallsByUserNotFound() throws ResourceNotFoundException {
        when(callService.getByUser(testUser)).thenThrow(new ResourceNotFoundException());
        callController.getCallsByUser(testUser);
    }

    @Test
    public void testGetCallsByPhonelineOk() throws ResourceNotFoundException {
        when(callService.getByPhoneline(testPhoneline)).thenReturn(testCalls);

        List<Call> calls = callController.getCallsByPhoneline(testPhoneline);

        assertEquals(1, calls.size());
        assertEquals(Integer.valueOf(1), calls.get(0).getId());
        verify(callService, times(1)).getByPhoneline(testPhoneline);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetCallsByPhonelineNotFound() throws ResourceNotFoundException {
        when(callService.getByPhoneline(testPhoneline)).thenThrow(new ResourceNotFoundException());
        callController.getCallsByPhoneline(testPhoneline);
    }

    @Test
    public void testGetCallsByUserBetweenOk() throws ResourceNotFoundException {
        when(callService.getByUserBetween(testUser,testDate,testDate)).thenReturn(testCalls);

        List<Call> calls = callController.getCallsByUserBetween(testUser,testDate,testDate);

        assertEquals(1, calls.size());
        assertEquals(Integer.valueOf(1), calls.get(0).getId());
        verify(callService, times(1)).getByUserBetween(testUser,testDate,testDate);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetCallsByUserBetweenNotFound() throws ResourceNotFoundException {
        when(callService.getByUserBetween(testUser,testDate,testDate)).thenThrow(new ResourceNotFoundException());
        callController.getCallsByUserBetween(testUser, testDate, testDate);
    }

    @Test
    public void testGetCallsByPhonelineBetweenOk() throws ResourceNotFoundException {
        when(callService.getByPhonelineBetween(testPhoneline,testDate,testDate)).thenReturn(testCalls);

        List<Call> calls = callController.getCallsByPhonelineBetween(testPhoneline,testDate,testDate);

        assertEquals(1, calls.size());
        assertEquals(Integer.valueOf(1), calls.get(0).getId());
        verify(callService, times(1)).getByPhonelineBetween(testPhoneline,testDate,testDate);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetCallsByPhonelineBetweenNotFound() throws ResourceNotFoundException {
        when(callService.getByPhonelineBetween(testPhoneline,testDate,testDate)).thenThrow(new ResourceNotFoundException());
        callController.getCallsByPhonelineBetween(testPhoneline, testDate, testDate);
    }

    @Test
    public void testGetCallsByUserMostCalledOk() throws ResourceNotFoundException {
        when(callService.getByUserMostCalled(testUser)).thenReturn(testMostCalledDtos);

        List<MostCalledDto> calls = callController.getCallsByUserMostCalled(testUser);

        assertEquals(1, calls.size());
        assertEquals(testMostCalledDto, calls.get(0));
        verify(callService, times(1)).getByUserMostCalled(testUser);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetCallsByUserMostCalledNotFound() throws ResourceNotFoundException {
        when(callService.getByUserMostCalled(testUser)).thenThrow(new ResourceNotFoundException());

        callController.getCallsByUserMostCalled(testUser);
    }

    @Test
    public void testGetCallsByPhonelineMostCalledOk() throws ResourceNotFoundException {
        when(callService.getByPhonelineMostCalled(testPhoneline)).thenReturn(testMostCalledDtos);

        List<MostCalledDto> calls = callController.getCallsByPhonelineMostCalled(testPhoneline);

        assertEquals(1, calls.size());
        assertEquals(testMostCalledDto, calls.get(0));
        verify(callService, times(1)).getByPhonelineMostCalled(testPhoneline);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetCallsByPhonelineMostCalledNotFound() throws ResourceNotFoundException {
        when(callService.getByPhonelineMostCalled(testPhoneline)).thenThrow(new ResourceNotFoundException());
        callController.getCallsByPhonelineMostCalled(testPhoneline);
    }

    @Test
    public void testNewCallOk() throws ResourceNotFoundException {
        when(callService.newCall(testCall)).thenReturn(testCall);

        Call call = callController.newCall(testCall);

        assertEquals(Integer.valueOf(1), call.getId());
        assertEquals(testPhoneline, call.getOrigin());
        verify(callService, times(1)).newCall(testCall);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testNewCallNotFound() throws ResourceNotFoundException {
        when(callService.newCall(testCall)).thenThrow(new ResourceNotFoundException());
        callController.newCall(testCall);
    }
}
