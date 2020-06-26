package com.utn.phones.controller.web;

import com.utn.phones.controller.CallController;
import com.utn.phones.controller.PhonelineController;
import com.utn.phones.dto.MostCalledDto;
import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import com.utn.phones.model.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static com.utn.phones.model.Phoneline.Status.*;
import static com.utn.phones.model.Phoneline.Type.*;
import static com.utn.phones.model.User.Role.*;
import static org.junit.Assert.assertEquals;
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
        testUser = new User(1,testCity,"nombre","apellido",123,"123", USER);
        testUser = new User(1,testCity,"nombre","apellido",123,"123", INFRAESTRUCTURE);
        testUser = new User(1,testCity,"nombre","apellido",123,"123", EMPLOYEE);
        testPhoneline = new Phoneline(1L,testUser,testCity,MOBILE,SUSPENDED);
        testPhoneline = new Phoneline(1L,testUser,testCity,LANDLINE,CANCELLED);
        testPhoneline = new Phoneline(1L,testUser,testCity,MOBILE,ACTIVE);
        testPhonelines = Collections.singletonList(testPhoneline);
        testCall = new Call(1,null,testPhoneline,testPhoneline,testDate,1.0f,10,1.0f);
        testCalls = Collections.singletonList(testCall);
        testMostCalledDto = new MostCalledDto(1L, 1L, 1L);
        testMostCalledDtos = Collections.singletonList(testMostCalledDto);
    }

    @Test
    public void testGetPhonelinesOk() {
        when(phonelineController.getPhonelines()).thenReturn(testPhonelines);

        ResponseEntity<List<Phoneline>> response = phonelineWebController.getPhonelines();

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(Long.valueOf(1), response.getBody().get(0).getId());
        assertEquals(testCity, response.getBody().get(0).getCity());
        verify(phonelineController, times(1)).getPhonelines();
    }

    @Test
    public void testGetPhonelinesNoContent() {
        when(phonelineController.getPhonelines()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Phoneline>> response = phonelineWebController.getPhonelines();

        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
        verify(phonelineController, times(1)).getPhonelines();
    }

    @Test
    public void testGetPhonelineOk() throws ResourceNotFoundException {
        when(phonelineController.getPhonelineById(1L)).thenReturn(testPhoneline);

        ResponseEntity<Phoneline> response = phonelineWebController.getPhonelineById(1L);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(testPhoneline, response.getBody());
        assertEquals(Long.valueOf(1), Objects.requireNonNull(response.getBody()).getId());
        assertEquals(testCity, response.getBody().getCity());
        verify(phonelineController, times(1)).getPhonelineById(1L);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetPhonelineNotFound() throws ResourceNotFoundException {
        when(phonelineController.getPhonelineById(1L)).thenThrow(new ResourceNotFoundException());
        phonelineWebController.getPhonelineById(1L);
    }

    @Test
    public void testGetPhonelineCallsOk() throws ResourceNotFoundException {
        when(phonelineController.getPhonelineById(1L)).thenReturn(testPhoneline);
        when(callController.getCallsByPhoneline(testPhoneline)).thenReturn(testCalls);

        ResponseEntity<List<Call>> response = phonelineWebController.getPhonelineCalls(1L);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(testCalls, response.getBody());
        assertEquals(Integer.valueOf(1), Objects.requireNonNull(response.getBody()).get(0).getId());
        assertEquals(testPhoneline, response.getBody().get(0).getOrigin());
        assertEquals(testPhoneline, response.getBody().get(0).getDestination());
        verify(phonelineController, times(1)).getPhonelineById(1L);
        verify(callController, times(1)).getCallsByPhoneline(testPhoneline);
    }

    @Test
    public void testGetPhonelineCallsNoContent() throws ResourceNotFoundException {
        when(phonelineController.getPhonelineById(1L)).thenReturn(testPhoneline);
        when(callController.getCallsByPhoneline(testPhoneline)).thenReturn(new ArrayList<>());

        ResponseEntity<List<Call>> response = phonelineWebController.getPhonelineCalls(1L);

        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
        verify(phonelineController, times(1)).getPhonelineById(1L);
        verify(callController, times(1)).getCallsByPhoneline(testPhoneline);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetPhonelineCallsNotFound() throws ResourceNotFoundException {
        when(callController.getCallsByPhoneline(phonelineController.getPhonelineById(1L))).thenThrow(new ResourceNotFoundException());
        phonelineWebController.getPhonelineCalls(1L);
    }

    @Test
    public void testGetPhonelineCallsBetweenOk() throws ResourceNotFoundException {
        when(phonelineController.getPhonelineById(1L)).thenReturn(testPhoneline);
        when(callController.getCallsByPhonelineBetween(testPhoneline,testDate,testDate)).thenReturn(testCalls);

        ResponseEntity<List<Call>> response = phonelineWebController.getPhonelineCallsBetween(1L,testDate,testDate);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(testCalls, response.getBody());
        assertEquals(Integer.valueOf(1), Objects.requireNonNull(response.getBody()).get(0).getId());
        assertEquals(testPhoneline, response.getBody().get(0).getOrigin());
        assertEquals(testPhoneline, response.getBody().get(0).getDestination());
        verify(phonelineController, times(1)).getPhonelineById(1L);
        verify(callController, times(1)).getCallsByPhonelineBetween(testPhoneline,testDate,testDate);
    }

    @Test
    public void testGetPhonelineCallsBetweenNoContent() throws ResourceNotFoundException {
        when(phonelineController.getPhonelineById(1L)).thenReturn(testPhoneline);
        when(callController.getCallsByPhonelineBetween(testPhoneline,testDate,testDate)).thenReturn(new ArrayList<>());

        ResponseEntity<List<Call>> response = phonelineWebController.getPhonelineCallsBetween(1L,testDate,testDate);

        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
        verify(phonelineController, times(1)).getPhonelineById(1L);
        verify(callController, times(1)).getCallsByPhonelineBetween(testPhoneline,testDate,testDate);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetPhonelineCallsBetweenNotFound() throws ResourceNotFoundException {
        when(callController.getCallsByPhonelineBetween(phonelineController.getPhonelineById(1L),testDate,testDate)).thenThrow(new ResourceNotFoundException());
        phonelineWebController.getPhonelineCallsBetween(1L,testDate,testDate);
    }

    @Test
    public void testGetPhonelineMostCalledOk() throws ResourceNotFoundException {
        when(phonelineController.getPhonelineById(1L)).thenReturn(testPhoneline);
        when(callController.getCallsByPhonelineMostCalled(testPhoneline)).thenReturn(testMostCalledDtos);

        ResponseEntity<List<MostCalledDto>> response = phonelineWebController.getPhonelineMostCalled(1L);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(testMostCalledDtos, response.getBody());
        assertEquals(Long.valueOf(1), Objects.requireNonNull(Objects.requireNonNull(response.getBody())).get(0).getDestination());
        verify(phonelineController, times(1)).getPhonelineById(1L);
        verify(callController, times(1)).getCallsByPhonelineMostCalled(testPhoneline);
    }

    @Test
    public void testGetPhonelineMostCalledNoContent() throws ResourceNotFoundException {
        when(phonelineController.getPhonelineById(1L)).thenReturn(testPhoneline);
        when(callController.getCallsByPhonelineMostCalled(testPhoneline)).thenReturn(new ArrayList<>());

        ResponseEntity<List<MostCalledDto>> response = phonelineWebController.getPhonelineMostCalled(1L);

        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
        verify(phonelineController, times(1)).getPhonelineById(1L);
        verify(callController, times(1)).getCallsByPhonelineMostCalled(testPhoneline);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetPhonelineMostCalledNotFound() throws ResourceNotFoundException {
        when(callController.getCallsByPhonelineMostCalled(phonelineController.getPhonelineById(1L))).thenThrow(new ResourceNotFoundException());
        phonelineWebController.getPhonelineMostCalled(1L);
    }

    @Test
    public void testNewPhonelineOk() throws ResourceAlreadyExistsException {
        when(phonelineController.newPhoneline(testPhoneline)).thenReturn(testPhoneline);

        ResponseEntity<Phoneline> response = phonelineWebController.newPhoneline(testPhoneline);

        assertEquals(Long.valueOf(1), Objects.requireNonNull(response.getBody()).getId());
        assertEquals(testPhoneline, response.getBody());
        verify(phonelineController, times(1)).newPhoneline(testPhoneline);
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void testNewPhonelineAlreadyExists() throws ResourceAlreadyExistsException {
        when(phonelineController.newPhoneline(testPhoneline)).thenThrow(new ResourceAlreadyExistsException());
        phonelineWebController.newPhoneline(testPhoneline);
    }
}
