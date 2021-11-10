package com.JavaSchoolProject.SocialNetwork.controllers.userwall;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostInfo {
    private String txt;
    private String dateAndTime;
    private long numberOfLikes;
}
