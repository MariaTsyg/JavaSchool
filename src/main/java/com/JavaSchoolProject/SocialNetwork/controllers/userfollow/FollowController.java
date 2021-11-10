package com.JavaSchoolProject.SocialNetwork.controllers.userfollow;

import com.JavaSchoolProject.SocialNetwork.entities.CustomUser;
import com.JavaSchoolProject.SocialNetwork.entities.FollowingDTO;
import com.JavaSchoolProject.SocialNetwork.ajaxmodels.AjaxFollowModel;
import com.JavaSchoolProject.SocialNetwork.repositories.CustomUserRepository;
import com.JavaSchoolProject.SocialNetwork.repositories.FollowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class FollowController {

    private final CustomUserRepository customUserRepository;
    private final FollowingRepository followingRepository;

    @Autowired
    public FollowController(CustomUserRepository customUserRepository, FollowingRepository followingRepository) {
        this.customUserRepository = customUserRepository;
        this.followingRepository = followingRepository;
    }


    @PostMapping("/following")
    @ResponseBody
    public ResponseEntity setFollowing(@RequestBody AjaxFollowModel ajaxFollowModel, Principal principal) {
        CustomUser follower = customUserRepository.findByUsername(principal.getName());
        CustomUser leadUser = customUserRepository.findByAccountId(ajaxFollowModel.getAccount_id());
        if(followingRepository.existsByFollowerIdAndUserId(follower.getId(), leadUser.getId())) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE); // 406 - такая пара уже есть
        }

        if(!follower.getId().equals(leadUser.getId())) {
            FollowingDTO followingDTO = new FollowingDTO(follower.getId(), leadUser.getId());
            followingRepository.save(followingDTO);
            return new ResponseEntity(HttpStatus.OK); // 200
        } else {
            return new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED); // 405 - подписка на самого себя
        }
    }
}
