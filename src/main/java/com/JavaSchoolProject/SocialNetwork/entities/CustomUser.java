package com.JavaSchoolProject.SocialNetwork.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users_table")
@Getter
@Setter
@ToString
public class CustomUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id")
    private String accountId;

    private String username;
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "image_name")
    private String imageName;
    @ManyToMany
    @JoinTable(
            name = "users_and_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<CustomRole> customRoles = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "users_likes_posts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Set<PostDTO> likedPosts = new HashSet<>();
}
