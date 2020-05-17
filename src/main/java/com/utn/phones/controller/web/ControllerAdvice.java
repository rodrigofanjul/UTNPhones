package com.utn.phones.controller.web;

import com.utn.phones.dto.ErrorResponseDto;
import com.utn.phones.exception.InvalidLoginException;
import com.utn.phones.exception.ResourceAlreadyExistsException;
import com.utn.phones.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("errors", ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.toList()));
        body.put("path", ((HttpServletRequest)request).getRequestURI());
        return new ResponseEntity<>(body, headers, status);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidLoginException.class)
    public ErrorResponseDto handleInvalidLoginException(HttpServletRequest request, InvalidLoginException ex) {
        return new ErrorResponseDto(new Date(), HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ResourceNotFoundException.class, ResourceAlreadyExistsException.class})
    public ErrorResponseDto handleException(HttpServletRequest request, Exception ex) {
        return new ErrorResponseDto(new Date(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
    }
}