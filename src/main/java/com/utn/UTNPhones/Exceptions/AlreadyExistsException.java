package com.utn.UTNPhones.Exceptions;

public class AlreadyExistsException extends Exception{

    public AlreadyExistsException(String type, int id){
        super(String.format("Resource {%s} with {id:%d} already exists", type, id));
    }
}