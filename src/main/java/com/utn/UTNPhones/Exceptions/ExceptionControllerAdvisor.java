package com.utn.UTNPhones.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, HandlerMethod handlerMethod, WebRequest request) {

        String controllerName = handlerMethod.getMethod().getDeclaringClass().toString();
        String methodName = handlerMethod.getMethod().getName();

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        body.put("controller", controllerName);
        body.put("action", methodName);

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Object> handleAlreadyExistsException(AlreadyExistsException ex, HandlerMethod handlerMethod, WebRequest request) {

        String controllerName = handlerMethod.getMethod().getDeclaringClass().toString();
        String methodName = handlerMethod.getMethod().getName();

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        body.put("controller", controllerName);
        body.put("action", methodName);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullArgumentException.class)
    public ResponseEntity<Object> handleNullArgumentException(NullArgumentException ex, HandlerMethod handlerMethod, WebRequest request) {

        String controllerName = handlerMethod.getMethod().getDeclaringClass().toString();
        String methodName = handlerMethod.getMethod().getName();

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        body.put("controller", controllerName);
        body.put("action", methodName);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}