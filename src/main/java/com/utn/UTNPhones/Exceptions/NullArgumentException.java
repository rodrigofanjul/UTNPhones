package com.utn.UTNPhones.Exceptions;

public class NullArgumentException extends Exception{

    public NullArgumentException(){
        super(String.format("An argument cannot be null."));
    }
}
