package com.ipdive.springboottemplate.exceptions;

public class CompanyNotFoundException extends Exception {

    private final static String MESSAGE = "Company {%s} not found";
    private final String TYPE = "companyException";

    public CompanyNotFoundException(String ticker) {
        super(MESSAGE);
    }

    public String getType() {
        return TYPE;
    }
}