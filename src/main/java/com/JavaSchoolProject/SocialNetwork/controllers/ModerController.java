package com.JavaSchoolProject.SocialNetwork.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ModerController {
    @GetMapping("/moder")
    public String getModerPage() {
        return "moder/moder_page";
    }
}
