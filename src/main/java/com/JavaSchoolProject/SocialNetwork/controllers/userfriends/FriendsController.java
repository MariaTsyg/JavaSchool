package com.JavaSchoolProject.SocialNetwork.controllers.userfriends;

import com.JavaSchoolProject.SocialNetwork.entities.CustomUser;
import com.JavaSchoolProject.SocialNetwork.entities.FollowingDTO;
import com.JavaSchoolProject.SocialNetwork.ajaxmodels.AjaxFriendModel;
import com.JavaSchoolProject.SocialNetwork.repositories.CustomUserRepository;
import com.JavaSchoolProject.SocialNetwork.repositories.FollowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class FriendsController {

    private final CustomUserRepository customUserRepository;
    private final FollowingRepository followingRepository;

    @Autowired
    public FriendsController(CustomUserRepository customUserRepository, FollowingRepository followingRepository) {
        this.customUserRepository = customUserRepository;
        this.followingRepository = followingRepository;
    }

    @GetMapping("/friends")
    public String getUserFriendsPage(Principal principal, Model model) {
        Long principalId = customUserRepository.findByUsername(principal.getName()).getId();

        List<FollowingDTO> friendsDTO = followingRepository.findAllByFollowerId(principalId);

        List<AjaxFriendModel> friends = friendsDTO.stream()
                .map(friendDTO -> {
                    CustomUser user = customUserRepository.findById(friendDTO.getUserId()).get();

                    AjaxFriendModel ajaxFriend = new AjaxFriendModel();
                    ajaxFriend.setFio(user.getFirstName() + " " + user.getLastName());
                    ajaxFriend.setAccountId(user.getAccountId());
                    if(user.getImageName() != null) {
                        ajaxFriend.setImgLink("/image/" + user.getImageName());
                    } else {
                        ajaxFriend.setImgLink("https://bipbap.ru/wp-content/uploads/2017/10/3-6.png");
                    }
                    return ajaxFriend;
                }).collect(Collectors.toList());

        model.addAttribute("friends", friends);
        return "user/user_friends_page";
    }

    @PostMapping("/friends")
    @ResponseBody
    public List<AjaxFriendModel> getFilteredFriends(@RequestBody FilterFIOForFriendsPage filterObj, Principal principal) {
        System.out.println(filterObj.toString());

        List<CustomUser> filteredCustomUsers = customUserRepository.findAllByFirstNameAndLastNameContains(filterObj.getFilterName(), filterObj.getFilterLastName());
        List<AjaxFriendModel> filteredAjaxUsers = filteredCustomUsers.stream().map(customUser -> {
            if(customUser.getUsername() == principal.getName()) {
                return null;
            }

            AjaxFriendModel ajaxFriend = new AjaxFriendModel();
            ajaxFriend.setFio(customUser.getFirstName() + " " + customUser.getLastName());
            ajaxFriend.setAccountId(customUser.getAccountId());
            if(customUser.getImageName() != null) {
                ajaxFriend.setImgLink("/image/" + customUser.getImageName());
            } else {
                ajaxFriend.setImgLink("https://bipbap.ru/wp-content/uploads/2017/10/3-6.png");
            }

            return ajaxFriend;

        }).collect(Collectors.toList());

        filteredAjaxUsers.forEach(ajaxFriendModel -> System.out.println(ajaxFriendModel));
        return filteredAjaxUsers;
    }
}
