package com.JavaSchoolProject.SocialNetwork.controllers.testControllers;


import com.JavaSchoolProject.SocialNetwork.entities.CustomUser;
import com.JavaSchoolProject.SocialNetwork.repositories.CustomUserRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.Principal;
import java.util.UUID;

@Controller
public class TestImageController {
    private final CustomUserRepository userRepository;

    @Autowired
    public TestImageController(CustomUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/test_img")
    public String getTestImgTestController(Model model){
        model.addAttribute("modelForm", new UploadImageModel());
        return "test/test_image_page";
    }

    @PostMapping("/test_img")
    public String uploadImage(UploadImageModel uploadImageModel, Principal principal){
        CustomUser  user = userRepository.findByUsername(principal.getName());

        String fileName = uploadImageModel.getMultipartFile().getOriginalFilename();

        UUID uuid = UUID.randomUUID();
        String imageName = String.format("%s.%s",  uuid.toString() ,  fileName.split("\\.")[1]);

        String path = String.format("C:\\temp\\%s",imageName);


        File file = new File(path);
        try(FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(uploadImageModel.getMultipartFile().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/me";
        }

        user.setImageName(imageName);
        userRepository.save(user);

        return "redirect:/me";
    }


    @GetMapping("/image/{name}")
    public void getImageAsByteArray(@PathVariable String name, HttpServletResponse response, Principal principal) throws IOException {
            String path = "C:\\temp\\"+name;

            InputStream in = new FileInputStream(path);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            IOUtils.copy(in, response.getOutputStream());

    }
}
