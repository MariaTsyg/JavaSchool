package com.JavaSchoolProject.SocialNetwork.controllers.testControllers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UploadImageModel {
    private MultipartFile multipartFile;
}
