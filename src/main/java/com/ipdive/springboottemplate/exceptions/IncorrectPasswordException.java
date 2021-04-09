package com.ipdive.springboottemplate.exceptions;

public class IncorrectPasswordException extends UserException {

    private final static String MESSAGE = "Incorrect password entered";

    public IncorrectPasswordException() {
        super(MESSAGE);
    }
}
