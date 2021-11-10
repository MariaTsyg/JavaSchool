package com.JavaSchoolProject.SocialNetwork.controllers;

import com.JavaSchoolProject.SocialNetwork.entities.CustomRole;
import com.JavaSchoolProject.SocialNetwork.entities.CustomUser;
import com.JavaSchoolProject.SocialNetwork.repositories.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class RedirectController {
    private final CustomUserRepository customUserRepository;

    @Autowired
    public RedirectController(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @GetMapping("/defaultSuccessUrl")
    public String redirect(Principal principal) {
        if(principal == null || principal.getName()==null) {
            return "redirect:/login";
        }

        CustomUser user = customUserRepository.findByUsername(principal.getName());

        boolean isAdmin = false;
        boolean isModer = false;
        boolean isUser = false;

        for (CustomRole role : user.getCustomRoles()) {
            if(role.getRoleName().equals("ROLE_ADMIN")) {
                isAdmin = true;
            }
            if(role.getRoleName().equals("ROLE_MODER")) {
                isModer = true;
            }
            if(role.getRoleName().equals("ROLE_USER")) {
                isUser = true;
            }
        }

        if(isAdmin) {
            return "redirect:/admin";
        }
        if(isModer) {
            return "redirect:/moder";
        }
        if(isUser) {
            return "redirect:/user/wall";
        }

        return "redirect:/login";
    }
}
