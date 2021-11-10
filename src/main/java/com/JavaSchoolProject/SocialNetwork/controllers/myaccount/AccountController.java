package com.JavaSchoolProject.SocialNetwork.controllers.myaccount;

import com.JavaSchoolProject.SocialNetwork.entities.CustomRole;
import com.JavaSchoolProject.SocialNetwork.entities.CustomUser;
import com.JavaSchoolProject.SocialNetwork.entities.PostDTO;
import com.JavaSchoolProject.SocialNetwork.repositories.CustomUserRepository;
import com.JavaSchoolProject.SocialNetwork.repositories.FollowingRepository;
import com.JavaSchoolProject.SocialNetwork.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AccountController {

    private final CustomUserRepository userRepository;
    private final PostRepository postRepository;
    private final FollowingRepository followingRepository;

    @Autowired
    public AccountController(CustomUserRepository userRepository, PostRepository postRepository, FollowingRepository followingRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.followingRepository = followingRepository;
    }


    @GetMapping("/{user_id}")
    public String getAccountInfo(@PathVariable(name = "user_id") String accountId, Model model) {
        CustomUser user = userRepository.findByAccountId(accountId);
        if(user==null) {
            return "redirect:/404_1";
        }

        model.addAttribute("name", user.getFirstName()+" "+user.getLastName());
        model.addAttribute("posts_count", postRepository.countByUsername(user.getUsername()));
        List<PostDTO> postDTOS = postRepository.findAllByUsername(user.getUsername());
        long likesCount=0;
        for (PostDTO post : postDTOS) {
            likesCount+=post.getNumberOfLikes();
        }
        model.addAttribute("likes_count", likesCount);
        model.addAttribute("friends_count", followingRepository.findAllByFollowerId(user.getId()).size());

        return "user/user_account_page";
    }
}
