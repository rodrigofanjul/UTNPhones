package com.utn.phones.exception;

public class InvalidLoginException extends Exception{

    public InvalidLoginException(){
        super(String.format("Username or password are incorrect."));
    }
}
