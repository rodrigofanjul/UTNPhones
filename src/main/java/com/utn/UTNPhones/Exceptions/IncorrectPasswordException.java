package com.utn.UTNPhones.Exceptions;

import org.springframework.validation.Errors;

import static java.util.stream.Collectors.joining;

public class IncorrectPasswordException extends Exception{

    public IncorrectPasswordException(){
        super(String.format("Password supplied is incorrect."));
    }
}
