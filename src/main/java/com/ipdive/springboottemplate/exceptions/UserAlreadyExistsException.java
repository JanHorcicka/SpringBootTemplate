package com.ipdive.springboottemplate.exceptions;

import com.ipdive.springboottemplate.models.User;

public class UserAlreadyExistsException extends UserException {

    private final static String MESSAGE = "User already exists. Please sign in or request a password reset.";

    public UserAlreadyExistsException(User user) {
        super(MESSAGE);
    }
}
