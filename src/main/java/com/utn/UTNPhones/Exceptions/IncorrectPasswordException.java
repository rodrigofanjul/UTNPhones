package com.utn.UTNPhones.Exceptions;

public class IncorrectPasswordException extends Exception{

    public IncorrectPasswordException(){
        super(String.format("Username or password supplied are incorrect."));
    }
}
