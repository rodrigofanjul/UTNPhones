package com.utn.UTNPhones.Exceptions;

import org.springframework.dao.DataAccessException;

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(){
        super("Resource not found");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(int id) {
        super(String.format("Resource with id %d not found", id));
    }

    public ResourceNotFoundException(String message, int id) {
        super(String.format(message, id));
    }
}