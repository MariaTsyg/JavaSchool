package com.JavaSchoolProject.SocialNetwork.controllers.registration;

import com.JavaSchoolProject.SocialNetwork.entities.CustomRole;
import com.JavaSchoolProject.SocialNetwork.entities.CustomUser;
import com.JavaSchoolProject.SocialNetwork.repositories.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    private final CustomUserRepository customUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(CustomUserRepository customUserRepository, PasswordEncoder passwordEncoder) {
        this.customUserRepository = customUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        model.addAttribute("reg_model", new RegModel());
        return "other/registration_page";
    }

    @PostMapping("/registration")
    public String createNewUser(RegModel regModel) {
        CustomUser userCheck = customUserRepository.findByUsername(regModel.getUsername());
        if(userCheck != null) {
            return "redirect:/registration?userExists";
        }

        if(!regModel.getPassword().equals(regModel.getConfirmPassword())) {
            return "redirect:/registration?notEquals";
        }

        if(regModel.getUsername()==null||regModel.getUsername().equals("")||
                regModel.getFirstName()==null||regModel.getFirstName().equals("")||
                regModel.getLastName()==null||regModel.getLastName().equals("")||
                regModel.getPassword()==null||regModel.getPassword().equals("")) {
            return "redirect:/registration?emptyFields";
        }

        CustomUser customUser = new CustomUser();
        customUser.setUsername(regModel.getUsername());
        customUser.setFirstName(regModel.getFirstName());
        customUser.setLastName(regModel.getLastName());
        customUser.setPassword(passwordEncoder.encode(regModel.getPassword()));
        int userCount = customUserRepository.findAll().size();
        String generateID = String.format("id%05d", ++userCount +100);

        customUser.setAccountId(generateID);

        CustomRole customRole = new CustomRole();
        customRole.setId(1L);
        customRole.setRoleName("ROLE_USER");

        customUser.getCustomRoles().add(customRole);

        customUserRepository.save(customUser);

        return "redirect:/login?regSuccess";
    }
}
