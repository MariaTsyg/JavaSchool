package com.JavaSchoolProject.SocialNetwork.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "post_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String text;

    @Column(name = "number_of_likes")
    private long numberOfLikes;

    @Column(name = "publication_date")
    private Date date;
}
