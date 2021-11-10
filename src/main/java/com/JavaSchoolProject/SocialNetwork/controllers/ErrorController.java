package com.JavaSchoolProject.SocialNetwork.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorController {
    @RequestMapping(value = "/404_1", method = RequestMethod.GET)
    public String render404() {
        return "error/404_1";
    }
}
