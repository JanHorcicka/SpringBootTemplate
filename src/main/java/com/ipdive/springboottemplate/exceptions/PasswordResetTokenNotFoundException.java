package com.ipdive.springboottemplate.exceptions;

public class PasswordResetTokenNotFoundException extends Exception {

    private final static String MESSAGE = "PasswordResetToken with id {%s} not found";

    public PasswordResetTokenNotFoundException(String tokenId) {
        super(String.format(MESSAGE, tokenId));
    }
}
