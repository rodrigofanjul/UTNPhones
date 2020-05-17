package com.utn.phones.controller;

import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.*;
import com.utn.phones.service.CallService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.utn.phones.model.Phoneline.Status.ACTIVE;
import static com.utn.phones.model.Phoneline.Type.MOBILE;
import static com.utn.phones.model.User.Role.EMPLOYEE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class CallControllerTest {

    CallService callService;
    CallController callController;
    Date testDate;
    Phoneline testPhoneline;
    User testUser;
    Call testCall;
    List<Call> testCalls;

    @Before
    public void setUp() {
        callService = mock(CallService.class);
        callController = new CallController(callService);
        testDate = new Date();
        testUser = new User(1,new City(1,new Province(1,"Buenos Aires"),"Mar del Plata",223),"nombre","apellido",123,"123", EMPLOYEE);
        testPhoneline = new Phoneline(1l,testUser,testUser.getCity(),MOBILE,ACTIVE);
        testCall = new Call(1,null,testPhoneline,testPhoneline,testDate,null,10,null);
        testCalls = new ArrayList<Call>();
        testCalls.add(testCall);
    }

    @Test
    public void testGetCallsOk() throws ResourceNotFoundException {
        try {
            when(callService.getAll()).thenReturn(testCalls);
            List<Call> calls = callController.getCalls();
            assertEquals(1, calls.size());
            assertEquals(Integer.valueOf(1), calls.get(0).getId());
            verify(callService, times(1)).getAll();
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetCallsNotFound() throws ResourceNotFoundException {
        when(callService.getAll()).thenThrow(new ResourceNotFoundException());
        List<Call> calls = callController.getCalls();
    }

    @Test
    public void testGetCallsByUserOk() throws ResourceNotFoundException {
        try {
            when(callService.getByUser(testUser)).thenReturn(testCalls);
            List<Call> calls = callController.getCallsByUser(testUser);
            assertEquals(1, calls.size());
            assertEquals(Integer.valueOf(1), calls.get(0).getId());
            verify(callService, times(1)).getByUser(testUser);
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetCallsByUserNotFound() throws ResourceNotFoundException {
        when(callService.getByUser(testUser)).thenThrow(new ResourceNotFoundException());
        List<Call> call = callController.getCallsByUser(testUser);
    }

    @Test
    public void testGetCallsByUserBetweenOk() throws ResourceNotFoundException {
        try {
            when(callService.getByUserBetween(testUser,testDate,testDate)).thenReturn(testCalls);
            List<Call> calls = callController.getCallsByUserBetween(testUser,testDate,testDate);
            assertEquals(1, calls.size());
            assertEquals(Integer.valueOf(1), calls.get(0).getId());
            verify(callService, times(1)).getByUserBetween(testUser,testDate,testDate);
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetCallsByUserBetweenNotFound() throws ResourceNotFoundException {
        when(callService.getByUserBetween(testUser,testDate,testDate)).thenThrow(new ResourceNotFoundException());
        List<Call> call = callController.getCallsByUserBetween(testUser,testDate,testDate);
    }

    @Test
    public void testGetCallsByUserMostCalledOk() throws ResourceNotFoundException {
        try {
            when(callService.getByUserMostCalled(testUser)).thenReturn(testCalls);
            List<Call> calls = callController.getCallsByUserMostCalled(testUser);
            assertEquals(1, calls.size());
            assertEquals(Integer.valueOf(1), calls.get(0).getId());
            verify(callService, times(1)).getByUserMostCalled(testUser);
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetCallsByUserMostCalledNotFound() throws ResourceNotFoundException {
        when(callService.getByUserMostCalled(testUser)).thenThrow(new ResourceNotFoundException());
        List<Call> call = callController.getCallsByUserMostCalled(testUser);
    }

    @Test
    public void testNewCallOk() throws ResourceNotFoundException {
        try {
            when(callService.newCall(testCall)).thenReturn(testCall);
            Call call = callController.newCall(testCall);
            assertEquals(Integer.valueOf(1), call.getId());
            assertEquals(testPhoneline, call.getOrigin());
            verify(callService, times(1)).newCall(testCall);
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testNewCallNotFound() throws ResourceNotFoundException, ResourceAlreadyExistsException {
        when(callService.newCall(testCall)).thenThrow(new ResourceNotFoundException());
        Call call = callController.newCall(testCall);
    }
}
