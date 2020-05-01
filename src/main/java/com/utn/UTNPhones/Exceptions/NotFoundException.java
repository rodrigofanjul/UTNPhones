package com.utn.UTNPhones.Exceptions;

public class NotFoundException extends Exception {

    public NotFoundException(String type, int id) {
        super(String.format("Resource {%s} with {id:%d} not found", type, id));
    }
}