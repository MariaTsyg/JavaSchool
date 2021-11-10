package com.JavaSchoolProject.SocialNetwork.controllers.admin;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserForAdminPage {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private boolean isModer = false;
    private boolean isAdmin = false;
}
