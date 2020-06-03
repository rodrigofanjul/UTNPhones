package com.utn.phones.exception;

public class ResourceAlreadyExistsException extends Exception {

    public ResourceAlreadyExistsException(){
        super("Resource already exists");
    }

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

    public ResourceAlreadyExistsException(String message, int id) {
        super(String.format(message, id));
    }
}