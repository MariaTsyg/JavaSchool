package com.JavaSchoolProject.SocialNetwork.controllers.userfeed;

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
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/user")
public class FeedController {
    private final PostRepository postRepository;
    private final CustomUserRepository customUserRepository;
    private final FollowingRepository followingRepository;

    @Autowired
    public FeedController(PostRepository postRepository, CustomUserRepository customUserRepository, FollowingRepository followingRepository) {
        this.postRepository = postRepository;
        this.customUserRepository = customUserRepository;
        this.followingRepository = followingRepository;
    }

    @GetMapping("/feed")
    public String getUserFeedPage(Principal principal, Model model) {
        Long id = customUserRepository.findByUsername(principal.getName()).getId();
        List<FollowingDTO> allByFollowerId = followingRepository.findAllByFollowerId(id);

        List<PostFriendInfo> posts = new ArrayList<>();
        for(FollowingDTO following : allByFollowerId){
            CustomUser customUser = customUserRepository.findById(following.getUserId()).get();

            List<PostDTO> allPostsCurrentFriend = postRepository.findAllByUsername(customUser.getUsername());

            for(PostDTO postDTO : allPostsCurrentFriend){
                PostFriendInfo postInfo = new PostFriendInfo();
                postInfo.setPostId(postDTO.getId());
                postInfo.setFirstNAndLastN(customUser.getLastName()+ " " +customUser.getFirstName());
                postInfo.setImageName("/image/" + customUser.getImageName());
                postInfo.setTxt(postDTO.getText());


                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm yy-MM-dd");
                String dateAndTime = simpleDateFormat.format(postDTO.getDate());

                postInfo.setDateAndTime(dateAndTime);
                postInfo.setDateAndTimeToSort(postDTO.getDate());
                postInfo.setNumberOfLikes(postDTO.getNumberOfLikes());

                posts.add(postInfo);
            }
        }

        posts.sort((o1, o2) -> o2.getDateAndTimeToSort().compareTo(o1.getDateAndTimeToSort()));

        model.addAttribute("posts", posts);
        return "user/user_feed_page";
    }
}
