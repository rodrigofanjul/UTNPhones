package com.utn.phones.exception;

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(){
        super("Resource not found");
    }

    public ResourceNotFoundException(String message) { super(message); }

    public ResourceNotFoundException(String message, int id) { super(String.format(message, id)); }
}