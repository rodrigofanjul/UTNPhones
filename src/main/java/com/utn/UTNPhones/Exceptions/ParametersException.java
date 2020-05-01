package com.utn.UTNPhones.Exceptions;

public class ParametersException extends Exception{
private String error;
    public ParametersException(){
        super();
        this.error="A parameter cannot be null.";
    }

    @Override
    public String getMessage() {
        return error;
    }
}
