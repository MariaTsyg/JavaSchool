package com.JavaSchoolProject.SocialNetwork.controllers.userfeed;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostFriendInfo {
    private long postId;
    private String firstNAndLastN;
    private String imageName;
    private String txt;
    private String dateAndTime;
    private Date dateAndTimeToSort;
    private long numberOfLikes;
}
