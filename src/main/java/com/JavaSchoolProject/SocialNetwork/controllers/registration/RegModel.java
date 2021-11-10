package com.JavaSchoolProject.SocialNetwork.controllers.registration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegModel {
    private String username;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
}
