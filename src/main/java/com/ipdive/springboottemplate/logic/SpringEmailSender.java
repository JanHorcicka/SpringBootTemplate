package com.ipdive.springboottemplate.logic;

import com.ipdive.springboottemplate.controller.MyErrorController;
import com.ipdive.springboottemplate.models.ContactForm;
import com.ipdive.springboottemplate.models.PasswordResetToken;
import com.ipdive.springboottemplate.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SpringEmailSender {

    private static final Logger logger = LoggerFactory.getLogger(MyErrorController.class);

    private static JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("numberoneshares@gmail.com");
        mailSender.setPassword("ManGorFre1");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    public static boolean sendAccountValidationEmail(User user) {
        String emailBody = String.format(EmailBodyTemplates.ACCOUNT_VALIDATION_EMAIL.getEmailBody(), user.getUsername(), user.getUsername());
        String subject = EmailBodyTemplates.ACCOUNT_VALIDATION_EMAIL.getEmailSubject();
        String to = user.getUsername();
        return sendEmail(emailBody, subject, to);
    }

    public static boolean sendContactForm(ContactForm contactForm) {
        final String SENDER_EMAIL_ADDRESS = "info.ipdive@gmail.com";
        String emailBody = String.format(EmailBodyTemplates.CONTACT_FORM_EMAIL.getEmailBody(),
                contactForm.getFirstName(), contactForm.getLastName(), contactForm.getEmail(),
                contactForm.getPhone(), contactForm.getBody());
        String subject = EmailBodyTemplates.CONTACT_FORM_EMAIL.getEmailSubject();
        String to = SENDER_EMAIL_ADDRESS;
        return sendEmail(emailBody, subject, to);
    }

    public static boolean sendPasswordResetEmail(PasswordResetToken token) {
        String emailBody = String.format(EmailBodyTemplates.PASSWORD_RESET_EMAIL.getEmailBody(), token.getId(), token.getId());
        String subject = EmailBodyTemplates.PASSWORD_RESET_EMAIL.getEmailSubject();
        String to = token.getUser();
        return sendEmail(emailBody, subject, to);
    }

    private static boolean sendEmail(String body, String subject, String to) {
        JavaMailSender javaMailSender = getJavaMailSender();
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            mimeMessage.setContent(body, "text/html");
            mimeMessage.setSubject(subject);
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            javaMailSender.send(mimeMessage);

        } catch (MessagingException me) {
            me.printStackTrace();
            logger.error(String.format("MessagingException for user %s : %s", to, me.getMessage()));
            return false;
        } catch (MailAuthenticationException mae) {
            mae.printStackTrace();
            logger.error(String.format("MailAuthenticationException for user %s : %s", to, mae.getMessage()));
            return false;
        }
        return true;
    }
}
