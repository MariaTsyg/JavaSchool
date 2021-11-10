package com.JavaSchoolProject.SocialNetwork.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "followers_and_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FollowingDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "follower_id")
    private long followerId;

    @Column(name = "user_id")
    private long userId;

    public FollowingDTO(long followerId, long userId) {
        this.followerId = followerId;
        this.userId = userId;
    }
}
