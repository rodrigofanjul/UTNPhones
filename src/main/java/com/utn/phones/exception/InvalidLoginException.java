package com.utn.phones.exception;

public class InvalidLoginException extends Exception {

    public InvalidLoginException() { super("Username or password are incorrect."); }
}
