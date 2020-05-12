package com.utn.UTNPhones.Exceptions;

public class ResourceAlreadyExistsException extends Exception {

    public ResourceAlreadyExistsException(){
        super("Resource already exists");
    }

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

    public ResourceAlreadyExistsException(int id) {
        super(String.format("Resource with id %d already exists", id));
    }

    public ResourceAlreadyExistsException(String message, int id) {
        super(String.format(message, id));
    }
}