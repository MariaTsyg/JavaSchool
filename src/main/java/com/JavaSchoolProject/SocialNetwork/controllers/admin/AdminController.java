package com.JavaSchoolProject.SocialNetwork.controllers.admin;

import com.JavaSchoolProject.SocialNetwork.entities.CustomRole;
import com.JavaSchoolProject.SocialNetwork.entities.CustomUser;
import com.JavaSchoolProject.SocialNetwork.ajaxmodels.*;
import com.JavaSchoolProject.SocialNetwork.repositories.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {
    private final CustomUserRepository customUserRepository;

    @Autowired
    public AdminController(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @GetMapping("/admin")
    public String getAdminPage(Model model) {
        List<CustomUser> allUsersFromDatabase = customUserRepository.findAll();
        List<UserForAdminPage> usersForAdminPage = allUsersFromDatabase.stream().map(customUser -> {
            UserForAdminPage user = new UserForAdminPage();
            user.setId(customUser.getId());
            user.setUsername(customUser.getUsername());
            user.setFirstName(customUser.getFirstName());
            user.setLastName(customUser.getLastName());
            for (CustomRole role : customUser.getCustomRoles()) {
                if(role.getRoleName().equals("ROLE_MODER")) {
                    user.setModer(true);
                }
            }
            for (CustomRole role : customUser.getCustomRoles()) {
                if(role.getRoleName().equals("ROLE_ADMIN")) {
                    user.setAdmin(true);
                }
            }
            return user;
        }).collect(Collectors.toList());

        model.addAttribute("users", usersForAdminPage);
        return "admin/admin_page";
    }

    @PostMapping("/change/roles")
    @ResponseBody
    public AjaxRespModel changeRoleMeth(@RequestBody AjaxChangeRoleModel model) {
        CustomUser user = customUserRepository.findFirstById(model.getUserId());

        if(user!=null) {
            CustomRole roleModer = new CustomRole();
            roleModer.setId(2L);
            roleModer.setRoleName("ROLE_MODER");

            if(model.isModerStatus()) {
                user.getCustomRoles().add(roleModer);
                customUserRepository.save(user);
            } else {
                user.getCustomRoles().remove(roleModer);
                customUserRepository.save(user);
            }
        }
//        System.out.println(model.toString());
        return new AjaxRespModel("статусОк");
    }

    @PostMapping("/filter/username")
    @ResponseBody
    public List<UserForAdminPage> showFilteredUsersByUsername(@RequestBody FilterUsernameForAdminPage filterObj) {

        List<CustomUser> filteredUsersFromDatabase = customUserRepository.findAllByUsernameContains(filterObj.getFilter());
        List<UserForAdminPage> filteredUsersForAdminPage = filteredUsersFromDatabase.stream().map(customUser -> {
            UserForAdminPage user = new UserForAdminPage();
            user.setId(customUser.getId());
            user.setUsername(customUser.getUsername());
            user.setFirstName(customUser.getFirstName());
            user.setLastName(customUser.getLastName());
            for (CustomRole role : customUser.getCustomRoles()) {
                if(role.getRoleName().equals("ROLE_MODER")) {
                    user.setModer(true);
                }
            }
            for (CustomRole role : customUser.getCustomRoles()) {
                if(role.getRoleName().equals("ROLE_ADMIN")) {
                    user.setAdmin(true);
                }
            }
            return user;
        }).collect(Collectors.toList());


        filteredUsersForAdminPage.forEach(System.out::println);
        return filteredUsersForAdminPage;
//        return new UserForAdminPage(200L, "TwoHundred", "TwoHundred", "TwoHundred", true);
    }

    @PostMapping("/admin/remove")
    @ResponseBody
    public ResponseEntity removeUser(@RequestBody AjaxRemoveUserModel model){
        if(customUserRepository.findById(model.getId()).orElse(null)!=null){
            System.out.println("удаление юзера -> " + customUserRepository.findById(model.getId()).orElse(null));
            customUserRepository.deleteById(model.getId());
        }
        return new ResponseEntity(HttpStatus.resolve(200));
    }

}
