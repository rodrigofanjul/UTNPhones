package com.utn.UTNPhones.Exceptions;

public class UserExistsException extends Exception{
    private String error;
    public UserExistsException(){
        super();
        this.error="The users already exists.";
    }
    @Override
    public String getMessage() {
        return error;
    }
}