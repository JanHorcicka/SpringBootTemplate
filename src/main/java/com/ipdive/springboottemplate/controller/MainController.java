package com.ipdive.springboottemplate.controller;

import com.ipdive.springboottemplate.exceptions.UserAlreadyExistsException;
import com.ipdive.springboottemplate.exceptions.UserException;
import com.ipdive.springboottemplate.logic.BusinessLogic;
import com.ipdive.springboottemplate.models.ContactForm;
import com.ipdive.springboottemplate.models.PasswordResetToken;
import com.ipdive.springboottemplate.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private BusinessLogic businessLogic;

    @RequestMapping("/")
    public String root(@RequestParam Optional<String> ref) {
        if (ref.isPresent()) {
            return "redirect:/index?ref=" + ref.get();
        }
        else {
            return "redirect:/index" ;
        }
    }

    @RequestMapping("/index")
    public String index(@RequestParam Optional<String> ref, Model model, HttpServletResponse response) {
        if (ref.isPresent()) {
            int cookieMaxAge = 1209600; // 60 * 60 * 24 * 14 = 14 days
            Cookie cookie = new Cookie("referral", ref.get());
            cookie.setMaxAge(cookieMaxAge);
            response.addCookie(cookie);
        }
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "index";
    }

    @RequestMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String newUser(@RequestParam String username,
                          @RequestParam String newPassword,
                          @CookieValue(value = "referral", defaultValue = "") String referral,
                          Model model) {
        String message = "";
        try {
            User user = new User(username, newPassword, referral);
            businessLogic.createNewUser(user);
            message = "Your account has been created. Follow the instructions we sent to your email address " +
                    "to validate your account.";
        }
        catch (UserAlreadyExistsException uaee) {
            message = uaee.getMessage();
        }
        catch (UserException ue) {
            message = "There was a problem creating your account. Please contact us using the contact form";
        }
        model.addAttribute("message", message);
        return "login";
    }

    @RequestMapping("/legalDisclaimer")
    public String legalDisclaimer() {
        return "legalDisclaimer";
    }

    @RequestMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("contactForm", new ContactForm());
        return "contact";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

    @RequestMapping("/login-error")
    public String loginError(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                errorMessage = ex.getMessage();
            }
        }
        if (errorMessage == "Bad credentials") errorMessage = "Wrong username or password";
        model.addAttribute("message", errorMessage);
        return "login";
    }

    @RequestMapping("/activatePremium")
    public String activatePremium() {
        return "activatePremium";
    }

    @RequestMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
    }

    @PostMapping("/requestPasswordReset")
    public String requestPasswordReset(@RequestParam String email, Model model) {
        User user = businessLogic.getUserByEmail(email);
        System.out.println("Got myself a user " + user);
        if (user == null) {
            model.addAttribute("error", "We didn't find this user");
            return "forgotPassword";
        }
        boolean result = businessLogic.initiatePasswordReset(user);
        if (!result) {
            model.addAttribute("error", "There waas a problem sending the email");
        }
        return "resetEmailSent";
    }

    @GetMapping("/resetPassword")
    public String getPasswordResetPage(@RequestParam String token, Model model) {
        PasswordResetToken passwordResetToken = businessLogic.getPasswordResetTokenById(token);
        if (passwordResetToken == null) {
            model.addAttribute("error", "Could not find reset token");
        } else if (passwordResetToken.isExpired()) {
            model.addAttribute("error", "Reset Token is expired");
        } else {
            model.addAttribute("token", passwordResetToken.getId());
        }
        return "resetPassword";
    }

    @PostMapping("/setNewPassword")
    public String handlePasswordReset(@RequestParam String newPassword,
                                      @RequestParam String token,
                                      Model model) {
        PasswordResetToken passwordResetToken = businessLogic.getPasswordResetTokenById(token);
        businessLogic.updateUserPassword(passwordResetToken, newPassword);
        model.addAttribute("message", "Password changed successfully.");
        return "login";
    }

    @GetMapping("/validateAccount")
    public String validateAccount(@RequestParam String username, Model model) {
        boolean success = businessLogic.enableUser(username);
        String message;
        if (success) {
            message = "Your account has been validated. Please login.";
        } else {
            message = "There has been a problem with validating your account. Please contact us using the contact form.";
        }
        model.addAttribute("message", message);
        return "login";
    }

    @PostMapping("/contactForm")
    public String contactForm(@ModelAttribute ContactForm contactForm, Model model) {
        String message;
        boolean success = businessLogic.sendContactForm(contactForm);
        if (success) {
            message = "The message was sent successfully.";
        } else {
            message = "There was a problem sending the message. " +
                    "Please try again or send email to our email address in the footer.";
        }
        model.addAttribute("message", message);
        return "contact";
    }
}