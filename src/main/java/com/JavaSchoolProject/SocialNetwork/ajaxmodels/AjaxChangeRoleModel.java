package com.JavaSchoolProject.SocialNetwork.ajaxmodels;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AjaxChangeRoleModel {
    private Long userId;
    private boolean moderStatus;
}
