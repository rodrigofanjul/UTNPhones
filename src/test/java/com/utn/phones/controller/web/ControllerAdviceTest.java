package com.utn.phones.controller.web;

import com.utn.phones.dto.ErrorResponseDto;
import com.utn.phones.exception.InvalidLoginException;
import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class ControllerAdviceTest {

    HttpServletRequest httpServletRequest;
    ControllerAdvice controllerAdvice;

    Date testDate;
    InvalidLoginException testInvalidLoginException;
    ResourceAlreadyExistsException testAlreadyExistsException;
    ResourceNotFoundException testNotFoundException;
    ErrorResponseDto testErrorResponseDto;

    @Before
    public void setUp() {
        httpServletRequest = mock(HttpServletRequest.class);
        controllerAdvice = new ControllerAdvice();

        testDate = new Date();
        testInvalidLoginException = new InvalidLoginException();
        testNotFoundException = new ResourceNotFoundException("Error");
        testNotFoundException = new ResourceNotFoundException("Error",1);
        testNotFoundException = new ResourceNotFoundException();
        testAlreadyExistsException = new ResourceAlreadyExistsException("Error");
        testAlreadyExistsException = new ResourceAlreadyExistsException("Error",1);
        testAlreadyExistsException = new ResourceAlreadyExistsException();
        testErrorResponseDto = new ErrorResponseDto();
        testErrorResponseDto = new ErrorResponseDto(testDate,HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase(),"No estas autorizado.","/api");
    }

    @Test
    public void testHandleInvalidLoginExceptionOk() {
        ErrorResponseDto error = controllerAdvice.handleInvalidLoginException(httpServletRequest,testInvalidLoginException);
        assertEquals(testDate, error.getTimestamp());
        assertEquals(HttpStatus.UNAUTHORIZED.value(), error.getCode());
        assertEquals(HttpStatus.UNAUTHORIZED.getReasonPhrase(), error.getStatus());
        assertEquals("Username or password are incorrect.", error.getMessage());
    }

    @Test
    public void testHandleAlreadyExistsExceptionOk() {
        ErrorResponseDto error = controllerAdvice.handleAlreadyExistsException(httpServletRequest,testAlreadyExistsException);
        assertEquals(testDate, error.getTimestamp());
        assertEquals(HttpStatus.BAD_REQUEST.value(), error.getCode());
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), error.getStatus());
        assertEquals("Resource already exists", error.getMessage());
    }

    @Test
    public void testHandleNotFoundExceptionOk() {
        ErrorResponseDto error = controllerAdvice.handleNotFoundException(httpServletRequest,testNotFoundException);
        assertEquals(testDate, error.getTimestamp());
        assertEquals(HttpStatus.NOT_FOUND.value(), error.getCode());
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), error.getStatus());
        assertEquals("Resource not found", error.getMessage());
    }
}
