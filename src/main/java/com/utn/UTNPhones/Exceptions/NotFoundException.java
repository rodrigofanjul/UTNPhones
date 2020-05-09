package com.utn.UTNPhones.Exceptions;

public class NotFoundException extends Exception {

    public NotFoundException() {
        super(String.format("Resource not found"));
    }
}