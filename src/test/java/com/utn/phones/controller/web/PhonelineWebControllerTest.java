package com.utn.phones.controller.web;

import com.sun.org.apache.regexp.internal.RE;
import com.utn.phones.controller.CallController;
import com.utn.phones.controller.PhonelineController;
import com.utn.phones.dto.MostCalledDto;
import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.*;
import com.utn.phones.service.PhonelineService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.utn.phones.model.Phoneline.Status.ACTIVE;
import static com.utn.phones.model.Phoneline.Type.MOBILE;
import static com.utn.phones.model.User.Role.EMPLOYEE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class PhonelineWebControllerTest {

    PhonelineController phonelineController;
    CallController callController;
    PhonelineWebController phonelineWebController;

    Date testDate;
    City testCity;
    User testUser;
    Phoneline testPhoneline;
    List<Phoneline> testPhonelines;
    Call testCall;
    List<Call> testCalls;
    MostCalledDto testMostCalledDto;
    List<MostCalledDto> testMostCalledDtos;

    @Before
    public void setUp() {
        phonelineController = mock(PhonelineController.class);
        callController = mock(CallController.class);
        phonelineWebController = new PhonelineWebController(phonelineController,callController);

        testDate = new Date();
        testCity = new City(1,new Province(1,"Buenos Aires"),"Mar del Plata",223);
        testUser = new User(1,testCity,"nombre","apellido",123,"123", EMPLOYEE);
        testPhoneline = new Phoneline(1l,testUser,testCity,MOBILE,ACTIVE);
        testPhonelines = Arrays.asList(testPhoneline);
        testCall = new Call(1,null,testPhoneline,testPhoneline,testDate,1.0f,10,1.0f);
        testCalls = Arrays.asList(testCall);
        testMostCalledDto = new MostCalledDto(1l,1l,1l);
        testMostCalledDtos = Arrays.asList(testMostCalledDto);
    }

    @Test
    public void testGetPhonelinesOk() throws ResourceNotFoundException {
        try {
            when(phonelineController.getPhonelines()).thenReturn(testPhonelines);

            ResponseEntity<List<Phoneline>> response = phonelineWebController.getPhonelines();

            assertEquals(HttpStatus.OK,response.getStatusCode());
            assertEquals(1, response.getBody().size());
            assertEquals(Long.valueOf(1), response.getBody().get(0).getId());
            assertEquals(testCity, response.getBody().get(0).getCity());
            verify(phonelineController, times(1)).getPhonelines();
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test
    public void testGetPhonelinesNoContent() throws ResourceNotFoundException {
        try {
            when(phonelineController.getPhonelines()).thenReturn(new ArrayList<Phoneline>());

            ResponseEntity<List<Phoneline>> response = phonelineWebController.getPhonelines();

            assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
            verify(phonelineController, times(1)).getPhonelines();
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetPhonelinesNotFound() throws ResourceNotFoundException {
        when(phonelineController.getPhonelines()).thenThrow(new ResourceNotFoundException());
        ResponseEntity<List<Phoneline>> response = phonelineWebController.getPhonelines();
    }

    @Test
    public void testGetPhonelineOk() throws ResourceNotFoundException {
        try {
            when(phonelineController.getPhonelineById(1l)).thenReturn(testPhoneline);

            ResponseEntity<Phoneline> response = phonelineWebController.getPhonelineById(1l);

            assertEquals(HttpStatus.OK,response.getStatusCode());
            assertEquals(testPhoneline, response.getBody());
            assertEquals(Long.valueOf(1), response.getBody().getId());
            assertEquals(testCity, response.getBody().getCity());
            verify(phonelineController, times(1)).getPhonelineById(1l);
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetPhonelineNotFound() throws ResourceNotFoundException {
        when(phonelineController.getPhonelineById(1l)).thenThrow(new ResourceNotFoundException());
        ResponseEntity<Phoneline> response = phonelineWebController.getPhonelineById(1l);
    }

    @Test
    public void testGetPhonelineCallsOk() throws ResourceNotFoundException {
        try {
            when(phonelineController.getPhonelineById(1l)).thenReturn(testPhoneline);
            when(callController.getCallsByPhoneline(testPhoneline)).thenReturn(testCalls);

            ResponseEntity<List<Call>> response = phonelineWebController.getPhonelineCalls(1l);

            assertEquals(HttpStatus.OK,response.getStatusCode());
            assertEquals(testCalls, response.getBody());
            assertEquals(Integer.valueOf(1), response.getBody().get(0).getId());
            assertEquals(testPhoneline, response.getBody().get(0).getOrigin());
            assertEquals(testPhoneline, response.getBody().get(0).getDestination());
            verify(phonelineController, times(1)).getPhonelineById(1l);
            verify(callController, times(1)).getCallsByPhoneline(testPhoneline);
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetPhonelineCallsNotFound() throws ResourceNotFoundException {
        when(callController.getCallsByPhoneline(phonelineController.getPhonelineById(1l))).thenThrow(new ResourceNotFoundException());
        ResponseEntity<List<Call>> response = phonelineWebController.getPhonelineCalls(1l);
    }

    @Test
    public void testGetPhonelineCallsBetweenOk() throws ResourceNotFoundException {
        try {
            when(phonelineController.getPhonelineById(1l)).thenReturn(testPhoneline);
            when(callController.getCallsByPhonelineBetween(testPhoneline,testDate,testDate)).thenReturn(testCalls);

            ResponseEntity<List<Call>> response = phonelineWebController.getPhonelineCallsBetween(1l,testDate,testDate);

            assertEquals(HttpStatus.OK,response.getStatusCode());
            assertEquals(testCalls, response.getBody());
            assertEquals(Integer.valueOf(1), response.getBody().get(0).getId());
            assertEquals(testPhoneline, response.getBody().get(0).getOrigin());
            assertEquals(testPhoneline, response.getBody().get(0).getDestination());
            verify(phonelineController, times(1)).getPhonelineById(1l);
            verify(callController, times(1)).getCallsByPhonelineBetween(testPhoneline,testDate,testDate);
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetPhonelineCallsBetweenNotFound() throws ResourceNotFoundException {
        when(callController.getCallsByPhonelineBetween(phonelineController.getPhonelineById(1l),testDate,testDate)).thenThrow(new ResourceNotFoundException());
        ResponseEntity<List<Call>> response = phonelineWebController.getPhonelineCallsBetween(1l,testDate,testDate);
    }

    @Test
    public void testGetPhonelineMostCalledOk() throws ResourceNotFoundException {
        try {
            when(phonelineController.getPhonelineById(1l)).thenReturn(testPhoneline);
            when(callController.getCallsByPhonelineMostCalled(testPhoneline)).thenReturn(testMostCalledDtos);

            ResponseEntity<List<MostCalledDto>> response = phonelineWebController.getPhonelineMostCalled(1l);

            assertEquals(HttpStatus.OK,response.getStatusCode());
            assertEquals(testMostCalledDtos, response.getBody());
            assertEquals(Long.valueOf(1), response.getBody().get(0).getDestination());
            verify(phonelineController, times(1)).getPhonelineById(1l);
            verify(callController, times(1)).getCallsByPhonelineMostCalled(testPhoneline);
        }
        catch (ResourceNotFoundException ex) {
            fail();
        }
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetPhonelineMostCalledNotFound() throws ResourceNotFoundException {
        when(callController.getCallsByPhonelineMostCalled(phonelineController.getPhonelineById(1l))).thenThrow(new ResourceNotFoundException());
        ResponseEntity<List<MostCalledDto>> response = phonelineWebController.getPhonelineMostCalled(1l);
    }

    @Test
    public void testNewPhonelineOk() throws ResourceAlreadyExistsException {
        try {
            when(phonelineController.newPhoneline(testPhoneline)).thenReturn(testPhoneline);

            ResponseEntity<Phoneline> response = phonelineWebController.newPhoneline(testPhoneline);

            assertEquals(Long.valueOf(1), response.getBody().getId());
            assertEquals(testPhoneline, response.getBody());
            verify(phonelineController, times(1)).newPhoneline(testPhoneline);
        }
        catch (ResourceAlreadyExistsException ex) {
            fail();
        }
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void testNewPhonelineAlreadyExists() throws ResourceAlreadyExistsException {
        when(phonelineController.newPhoneline(testPhoneline)).thenThrow(new ResourceAlreadyExistsException());
        ResponseEntity<Phoneline> response = phonelineWebController.newPhoneline(testPhoneline);
    }
}
