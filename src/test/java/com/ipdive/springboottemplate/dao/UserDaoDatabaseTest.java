package com.ipdive.springboottemplate.dao;

import com.ipdive.springboottemplate.dao.user.UserDao;
import com.ipdive.springboottemplate.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDaoDatabaseTest {

    @Autowired
    private UserDao userDao;

    private final String USERNAME = "TEST_USER";
    private final String EXISTING_USERNAME = "admin";
    private final String NON_EXISTING_USERNAME = "user_" + Instant.now().toEpochMilli();

    @Test
    void getUserByUsername() {
        final String EXPECTED_MESSAGE = String.format("Cannot find user with username %s", NON_EXISTING_USERNAME);
        UsernameNotFoundException userException = assertThrows(UsernameNotFoundException.class, () -> {
            userDao.getUserByUsername(NON_EXISTING_USERNAME);
        });
        assertTrue(userException.getMessage().contains(EXPECTED_MESSAGE));
    }

    @Test
    void savesUser() {
        int before = userDao.getAllUsers().size();
        Random random = new Random();
        String randomString = USERNAME + random.nextInt(9999);
        long timeNow = Instant.now().getEpochSecond();
        User user = new User(randomString, "pass", true, "USER", true, timeNow, timeNow);
        try {
            userDao.save(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        int after = userDao.getAllUsers().size();
        assertEquals(before + 1, after);
        userDao.delete(user);
        after = userDao.getAllUsers().size();
        assertEquals(before, after);
    }

    @Test
    void getAllUsers() {
        List<User> userList = userDao.getAllUsers();
        assertTrue(userList.size() > 0);
    }

    @Test
    void disableAndEnableUser() {
        User user = userDao.getUserByUsername(USERNAME);
        assertTrue(user.isEnabled());
        userDao.disableUser(USERNAME);
        user = userDao.getUserByUsername(USERNAME);
        assertFalse(user.isEnabled());
        userDao.enableUser(USERNAME);
        user = userDao.getUserByUsername(USERNAME);
        assertTrue(user.isEnabled());
    }

    @Test
    void disableAndEnablePremium() {
        User user = userDao.getUserByUsername(USERNAME);
        assertFalse(user.isPremium());
        userDao.enablePremium(USERNAME, Instant.now().getEpochSecond());
        user = userDao.getUserByUsername(USERNAME);
        assertTrue(user.isPremium());
        userDao.disablePremium(USERNAME);
        user = userDao.getUserByUsername(USERNAME);
        assertFalse(user.isPremium());
    }

    @Test
    void updatePasswordTest() {
        User user = userDao.getUserByUsername(USERNAME);
        Random random = new Random();
        String newPassword = "PASSWORD_" + random.nextInt(1000);
        user.setPassword(newPassword);
        userDao.save(user);
        assertTrue(userDao.getUserByUsername(USERNAME).getPassword().equals(newPassword));
    }

    // @Test
    void enablePremium() {
        final String username = "";
        final int TTL_MONTHS = 1;
        long premiumUntil = Instant.now().plus(TTL_MONTHS, ChronoUnit.MONTHS).getEpochSecond();
        userDao.enablePremium(username, premiumUntil);
        System.out.println(String.format("Enabled premium for user %s until %s", username, premiumUntil));
    }
}