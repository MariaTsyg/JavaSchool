package com.JavaSchoolProject.SocialNetwork.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "roles_table")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CustomRole {

    @Id
    private Long id;

    @Column(name = "role_name")
    private String roleName;

}
