package com.utn.UTNPhones.Exceptions;

import org.springframework.validation.Errors;

import static java.util.stream.Collectors.joining;

public class NullArgumentException extends Exception{

    private Errors errors;

    public NullArgumentException(Errors errors){
        super(String.format("%d argument(s) cannot be null.", errors.getErrorCount()));
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
