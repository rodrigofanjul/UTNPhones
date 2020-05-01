package com.utn.UTNPhones.Exceptions;

public class NullArgumentException extends Exception{

    public NullArgumentException(String type){
        super(String.format("An argument in resource {%s} cannot be null.", type));
    }
}
