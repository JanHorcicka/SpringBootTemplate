package com.ipdive.springboottemplate.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH )
    public String handleError(HttpServletRequest request, Model model) {
        String UNAUTHORIZED_PAGE = "activatePremium";
        String ERROR_PAGE = "error_page";
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        String errorMessage = String.format("<html><body><h2>Error Page</h2><div>Status code: <b>%s</b></div>"
                        + "<div>Exception Message: <b>%s</b></div><body></html>",
                statusCode, exception==null? "N/A": exception.getMessage());
        model.addAttribute("error", errorMessage);
        System.out.println(errorMessage);
        String errorPage = statusCode == 403 ? UNAUTHORIZED_PAGE : ERROR_PAGE;
        return errorPage;
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}