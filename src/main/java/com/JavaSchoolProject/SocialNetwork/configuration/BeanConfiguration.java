package com.JavaSchoolProject.SocialNetwork.configuration;

import com.JavaSchoolProject.SocialNetwork.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfiguration {
    private final CustomUserDetails customUserDetails;

    @Autowired
    public BeanConfiguration(CustomUserDetails customUserDetails) {
        this.customUserDetails = customUserDetails;
    }

    @Bean
    public PasswordEncoder createPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(createPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(customUserDetails);
        return daoAuthenticationProvider;
    }
}
