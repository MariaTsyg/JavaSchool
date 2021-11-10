package com.JavaSchoolProject.SocialNetwork.controllers;

import com.JavaSchoolProject.SocialNetwork.entities.CustomUser;
import com.JavaSchoolProject.SocialNetwork.entities.PostDTO;
import com.JavaSchoolProject.SocialNetwork.repositories.CustomUserRepository;
import com.JavaSchoolProject.SocialNetwork.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Map;

@Controller
public class PostController {
    private final PostRepository postRepository;
    private final CustomUserRepository customUserRepository;

    @Autowired
    public PostController(PostRepository postRepository, CustomUserRepository customUserRepository) {
        this.postRepository = postRepository;
        this.customUserRepository = customUserRepository;
    }

    @Transactional // чтобы сразу подтягивался весь Set likedPosts
    @PostMapping("/post/like")
    public ResponseEntity<String> likePost(@RequestBody Map<String, Long> request, Principal principal){
        CustomUser principalUser = customUserRepository.findByUsername(principal.getName());
        Long principalId = principalUser.getId();

        Long postId = request.get("postId");
        PostDTO postDTO = postRepository.findById(postId).orElse(null);

        if(postDTO == null){
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }

        if(principalUser.getLikedPosts().contains(postDTO)) {
            return new ResponseEntity<>("Already liked by You", HttpStatus.BAD_REQUEST);
        }

        principalUser.getLikedPosts().add(postDTO);
        customUserRepository.save(principalUser);

        postDTO.setNumberOfLikes(postDTO.getNumberOfLikes()+1);
        postRepository.save(postDTO);

        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }
}
