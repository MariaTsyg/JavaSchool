package com.JavaSchoolProject.SocialNetwork.controllers.myaccount;

import com.JavaSchoolProject.SocialNetwork.controllers.testControllers.UploadImageModel;
import com.JavaSchoolProject.SocialNetwork.entities.CustomUser;
import com.JavaSchoolProject.SocialNetwork.entities.FollowingDTO;
import com.JavaSchoolProject.SocialNetwork.entities.PostDTO;
import com.JavaSchoolProject.SocialNetwork.repositories.CustomUserRepository;
import com.JavaSchoolProject.SocialNetwork.repositories.FollowingRepository;
import com.JavaSchoolProject.SocialNetwork.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class MyAccountController {
    private final CustomUserRepository userRepository;
    private final PostRepository postRepository;
    private final FollowingRepository followingRepository;

    @Autowired
    public MyAccountController(CustomUserRepository userRepository, PostRepository postRepository, FollowingRepository followingRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.followingRepository = followingRepository;
    }

    @GetMapping("/me")
    public String getUserPage(Principal principal, Model model) {
        CustomUser user = userRepository.findByUsername(principal.getName());


        model.addAttribute("name", user.getFirstName()+" "+user.getLastName());
        model.addAttribute("posts_count", postRepository.countByUsername(user.getUsername()));
        List<PostDTO> postDTOS = postRepository.findAllByUsername(user.getUsername());
        long likesCount=0;
        for (PostDTO post : postDTOS) {
            likesCount+=post.getNumberOfLikes();
        }
        model.addAttribute("likes_count", likesCount);
        model.addAttribute("friends_count", followingRepository.findAllByFollowerId(user.getId()).size());
        if(user.getImageName() != null) {
            model.addAttribute("avatar_image", user.getImageName());
        System.out.println(user.getImageName());
        } else {
            model.addAttribute("avatar_image", "jack_sparrow.jpg");
        }

        model.addAttribute("modelForm", new UploadImageModel());

        return "user/my_account_page";
    }
}
