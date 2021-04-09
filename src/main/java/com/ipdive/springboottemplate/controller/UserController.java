package com.ipdive.springboottemplate.controller;

import com.ipdive.springboottemplate.exceptions.IncorrectPasswordException;
import com.ipdive.springboottemplate.logic.BusinessLogic;
import com.ipdive.springboottemplate.models.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    BusinessLogic businessLogic;

    @RequestMapping("/account")
    public String premium(Principal principal, Model model) {
        String username = principal.getName();
        UserInfo userInfo = businessLogic.getInfoForUsername(username);
        model.addAttribute("userInfo", userInfo);
        return "user/account";
    }

    @RequestMapping("/security")
    public String premium() {
        return "user/security";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 Principal principal, Model model) {
        String username = principal.getName();
        String message;
        try {
            businessLogic.changeUserPassword(username, oldPassword, newPassword);
            message = "The password has been changed successfully.";
        }
        catch (IncorrectPasswordException e) {
            message = e.getMessage();
        }
        model.addAttribute("message", message);
        return "user/security";
    }
}
