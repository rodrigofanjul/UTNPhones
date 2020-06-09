package com.utn.phones.controller.web;

import com.utn.phones.controller.CallController;
import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.*;
import com.utn.phones.service.CallService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.utn.phones.model.Phoneline.Status.ACTIVE;
import static com.utn.phones.model.Phoneline.Type.MOBILE;
import static com.utn.phones.model.User.Role.EMPLOYEE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class CallWebControllerTest {

    CallController callController;
    CallWebController callWebController;

    Date testDate;
    City testCity;
    Phoneline testPhoneline;
    Invoice testInvoice;
    User testUser;
    Call testCall;
    List<Call> testCalls;

    @Before
    public void setUp() {
        callController = mock(CallController.class);
        callWebController = new CallWebController(callController);

        testDate = new Date();
        testCity = new City(1,new Province(1,"Buenos Aires"),"Mar del Plata",223);
        testUser = new User(1, testCity,"nombre","apellido",123,"123", EMPLOYEE);
        testPhoneline = new Phoneline(1l,testUser,testUser.getCity(),MOBILE,ACTIVE);
        testCall = new Call(1,testInvoice,testPhoneline,testPhoneline,testDate,1.0f,10,1.0f);
        testInvoice = new Invoice(1,new Phoneline(1l,testUser,testUser.getCity(),MOBILE,ACTIVE),1,1f,1.21f,testDate,false,testDate);
        testCalls = Arrays.asList(testCall);
    }

    @Test
    public void testGetCallsOk() throws ResourceNotFoundException {
        try {
            when(callController.getCalls()).thenReturn(testCalls);
            ResponseEntity<List<Call>> response = callWebController.getCalls();
            assertEquals(HttpStatus.OK,response.getStatusCode());
            assertEquals(1, response.getBody().size());
            assertEquals(Integer.valueOf(1), response.getBody().get(0).getId());
            assertEquals(testCity, response.getBody().get(0).getOrigin().getUser().getCity());
            verify(callController, times(1)).getCalls();
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetCallsNotFound() throws ResourceNotFoundException {
        when(callController.getCalls()).thenThrow(new ResourceNotFoundException());
        ResponseEntity<List<Call>> response = callWebController.getCalls();
    }

    @Test
    public void testNewCallOk() throws ResourceNotFoundException {
        try {
            when(callController.newCall(testCall)).thenReturn(testCall);
            ResponseEntity<Call> response = callWebController.newCall(testCall);
            assertEquals(Integer.valueOf(1), response.getBody().getId());
            assertEquals(testPhoneline, response.getBody().getOrigin());
            verify(callController, times(1)).newCall(testCall);
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testNewCallNotFound() throws ResourceNotFoundException, ResourceAlreadyExistsException {
        when(callController.newCall(testCall)).thenThrow(new ResourceNotFoundException());
        ResponseEntity<Call> response = callWebController.newCall(testCall);
    }
}
