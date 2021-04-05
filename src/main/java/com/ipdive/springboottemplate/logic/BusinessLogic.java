package com.ipdive.springboottemplate.logic;

import com.ipdive.springboottemplate.controller.MyErrorController;
import com.ipdive.springboottemplate.dao.company.CompanyDao;
import com.ipdive.springboottemplate.dao.passwordResetToken.PasswordResetTokenDao;
import com.ipdive.springboottemplate.dao.user.UserDao;
import com.ipdive.springboottemplate.exceptions.PasswordResetTokenNotFoundException;
import com.ipdive.springboottemplate.exceptions.UserAlreadyExistsException;
import com.ipdive.springboottemplate.exceptions.UserException;
import com.ipdive.springboottemplate.models.ContactForm;
import com.ipdive.springboottemplate.models.PasswordResetToken;
import com.ipdive.springboottemplate.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class BusinessLogic {

    private CompanyDao companyDao;
    private UserDao userDao;

    @Autowired
    private PasswordResetTokenDao passwordResetTokenDao;

    @Autowired
    public void setCompanyDao(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private static final Logger logger = LoggerFactory.getLogger(BusinessLogic.class);

    public boolean createNewUser(User user) throws UserException {
        user.setAuthorities("USER");
        User existingUser = null;
        try {
            existingUser = userDao.getUserByUsername(user.getUsername());
        }
        catch (UsernameNotFoundException e) {
            logger.info(String.format("Creating new user: %s", user.getUsername()));
        }
        if (existingUser != null) throw new UserAlreadyExistsException(user);
        if (user.getUsername().contains("TEST")) user.setEnabled(true);
        setUserPassword(user, user.getPassword());
        userDao.save(user);
        boolean success = SpringEmailSender.sendAccountValidationEmail(user);
        if (success) logger.info(String.format("User %s was created.", user.getUsername()));
        return success;
    }

    public User getUserByEmail(String email) {
        System.out.println("Getting user by email");
        return userDao.getUserByUsername(email);
    }

    public boolean initiatePasswordReset(User user) {
        System.out.println("Start password reset");
        PasswordResetToken token = new PasswordResetToken(user);
        System.out.println("Token is " + token);
        savePasswordResetToken(token);
        boolean sendEmailSuccess = SpringEmailSender.sendPasswordResetEmail(token);
        return sendEmailSuccess;
    }

    private void savePasswordResetToken(PasswordResetToken token) {
        passwordResetTokenDao.save(token);
    }

    public PasswordResetToken getPasswordResetTokenById(String tokenId) {
        PasswordResetToken passwordResetToken = null;
        try {
            passwordResetToken = passwordResetTokenDao.getPasswordResetToken(tokenId);
            long timeNow = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
            if (timeNow > passwordResetToken.getExpirationDateEpoch()) passwordResetToken.setExpired(true);
        } catch (PasswordResetTokenNotFoundException e) {
            e.printStackTrace();
        }
        return passwordResetToken;
    }

    public void updateUserPassword(PasswordResetToken passwordResetToken, String password) {
        String userName = passwordResetToken.getUser();
        User user = getUserByEmail(userName);
        setUserPassword(user, password);
        userDao.save(user);
        deletePasswordResetToken(passwordResetToken);
    }

    private void setUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
    }

    public void deletePasswordResetToken(PasswordResetToken passwordResetToken) {
        passwordResetTokenDao.deletePasswordResetToken(passwordResetToken);
    }

    public boolean sendContactForm(ContactForm contactForm) {
        return SpringEmailSender.sendContactForm(contactForm);
    }

    public boolean enableUser(String username) {
        User user = userDao.getUserByUsername(username);
        user.setEnabled(true);
        boolean success = true;
        try {
            userDao.save(user);
        } catch (Exception e) {
            success = false;
        }
        return success;
    }

    public void deleteExpiredUsers() {
        List<User> users = userDao.getAllUsers();
        long expireDate = LocalDate.now().minusDays(1).toEpochDay();
        for (User user : users) {
            if (!user.isEnabled() && user.getDateCreatedEpoch() < expireDate) {
                userDao.delete(user);
            }
        }
    }

    public void deleteExpiredPasswordResetTokens() {
        List<PasswordResetToken> tokens = passwordResetTokenDao.getAll();
        long dateNow = LocalDate.now().toEpochDay();
        for (PasswordResetToken passwordResetToken : tokens) {
            if (passwordResetToken.getExpirationDateEpoch() < dateNow) {
                passwordResetTokenDao.deletePasswordResetToken(passwordResetToken);
            }
        }
    }
}
