package com.JavaSchoolProject.SocialNetwork.controllers.userwall;

import com.JavaSchoolProject.SocialNetwork.entities.PostDTO;
import com.JavaSchoolProject.SocialNetwork.ajaxmodels.userwall.AjaxSendPostModel;
import com.JavaSchoolProject.SocialNetwork.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class WallController {

    private final PostRepository postRepository;

    @Autowired
    public WallController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @GetMapping("/wall")
    public String getUserWallPage(Model model, Principal principal) {
        List<PostDTO> postsListFromDB = postRepository.findAllByUsernameOrderByDateDesc(principal.getName());
        model.addAttribute("posts", convertPostDTOToPostInfo(postsListFromDB));

        return "user/user_wall_page";
    }

    private List<PostInfo> convertPostDTOToPostInfo(List<PostDTO> postDTO){
        return postDTO.stream().map(p->{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm yy-MM-dd");
            String dateAndTime = simpleDateFormat.format(p.getDate());
            return new PostInfo(p.getText(), dateAndTime, p.getNumberOfLikes());
        }).collect(Collectors.toList());
    }

    @PostMapping("/wall")
    @ResponseBody
    public ResponseEntity<PostInfo> sendPost(@RequestBody AjaxSendPostModel postModel, Principal principal) {
       /* System.out.println("Текст поста ↓ ");
        System.out.println(postModel.getText());*/

        PostDTO post = new PostDTO();
        post.setUsername(principal.getName());
        post.setText(postModel.getText());
        post.setNumberOfLikes(0);
        post.setDate(new Date(System.currentTimeMillis()));

        postRepository.save(post);

        PostInfo req = new PostInfo();
        req.setTxt(post.getText());


        req.setTxt(post.getText());
        req.setDateAndTime(
                new SimpleDateFormat("HH:mm yy-MM-dd")
                        .format(post.getDate())
        );

        req.setNumberOfLikes(0);

        return new ResponseEntity<>(req, HttpStatus.OK);
    }
}
