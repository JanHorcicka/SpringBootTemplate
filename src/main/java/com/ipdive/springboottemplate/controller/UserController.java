package com.ipdive.springboottemplate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    /*@PostMapping("/updatePassword")
    @PreAuthorize("hasRole('READ_PRIVILEGE')")
    public GenericResponse changeUserPassword(Locale locale,
                                              @RequestParam("password") String password,
                                              @RequestParam("oldpassword") String oldPassword) {
        User user = userService.findUserByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName());

        if (!userService.checkIfValidOldPassword(user, oldPassword)) {
            throw new InvalidOldPasswordException();
        }
        userService.changeUserPassword(user, password);
        return new GenericResponse(messages.getMessage("message.updatePasswordSuc", null, locale));
    }

    @PostMapping("/user/resetPassword")
    public GenericResponse resetPassword(HttpServletRequest request,
                                         @RequestParam("email") String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException();
        }
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
        mailSender.send(constructResetTokenEmail(getAppUrl(request),
                request.getLocale(), token, user));
        return new GenericResponse(
                messages.getMessage("message.resetPasswordEmail", null,
                        request.getLocale()));
    }

    @GetMapping("/user/changePassword")
    public String showChangePasswordPage(Locale locale, Model model,
                                         @RequestParam("token") String token) {
        String result = securityService.validatePasswordResetToken(token);
        if(result != null) {
            String message = messages.getMessage("auth.message." + result, null, locale);
            return "redirect:/login.html?lang="
                    + locale.getLanguage() + "&message=" + message;
        } else {
            model.addAttribute("token", token);
            return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
        }
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    private SimpleMailMessage constructResetTokenEmail(
            String contextPath, Locale locale, String token, User user) {
        String url = contextPath + "/user/changePassword?token=" + token;
        String message = messages.getMessage("message.resetPassword",
                null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body,
                                             User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }*/
}
