package com.ipdive.springboottemplate.logic;

import com.ipdive.springboottemplate.exceptions.UserAlreadyExistsException;
import com.ipdive.springboottemplate.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BusinessLogicTest {

    private String WORKING_USERNAME = "numberoneshares@gmail.com";
    private String NEW_PASSWORD = "Password1";

    @Autowired
    BusinessLogic businessLogic;


    @Test
    void createNewUserWhenExistsTest() {
        final String EXPECTED_MESSAGE = "User already exists. Please sign in or request a password reset.";
        User user = new User(WORKING_USERNAME);
        UserAlreadyExistsException userException = assertThrows(UserAlreadyExistsException.class, () -> {
            businessLogic.createNewUser(user);
        });
        assertTrue(userException.getMessage().contains(EXPECTED_MESSAGE));
    }
}