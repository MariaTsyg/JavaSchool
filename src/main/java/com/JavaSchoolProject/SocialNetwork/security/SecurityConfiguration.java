package com.JavaSchoolProject.SocialNetwork.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.authorizeRequests()
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/moder").hasRole("MODER")
                .antMatchers("/admin").hasRole("ADMIN")
//                .antMatchers("/admin").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/scripts/**").permitAll()
                .antMatchers("/styles/**").permitAll()
                .antMatchers("/change/roles").permitAll()
                .antMatchers("/filter/username").permitAll()
                .antMatchers("/admin/remove").permitAll()
                .anyRequest().authenticated()
//        .and().httpBasic()
        .and().formLogin().loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/defaultSuccessUrl",true)
                .permitAll();
        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));//deleteCookies().logoutUrl("/logout").permitAll();

    }
}
