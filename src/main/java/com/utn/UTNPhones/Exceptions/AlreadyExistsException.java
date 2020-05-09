package com.utn.UTNPhones.Exceptions;

public class AlreadyExistsException extends Exception{

    public AlreadyExistsException(){
        super(String.format("Resource already exists"));
    }
}