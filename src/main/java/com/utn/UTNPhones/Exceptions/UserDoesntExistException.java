package com.utn.UTNPhones.Exceptions;

public class UserDoesntExistException extends Exception{
    private String error;
    public UserDoesntExistException(){
        super();
        this.error="The users doesn't exist.";
    }
    @Override
    public String getMessage() {
        return error;
    }
}