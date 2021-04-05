package com.ipdive.springboottemplate.controller;

import com.ipdive.springboottemplate.logic.BusinessLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/premium")
public class PremiumController {

    @Autowired
    BusinessLogic businessLogic;

    @RequestMapping("/premium")
    public String premium(@RequestParam(defaultValue = "1") int page, Model model) {
        return "premium/premium";
    }
}
