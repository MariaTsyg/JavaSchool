package com.JavaSchoolProject.SocialNetwork.security;

import com.JavaSchoolProject.SocialNetwork.entities.CustomRole;
import com.JavaSchoolProject.SocialNetwork.entities.CustomUser;
import com.JavaSchoolProject.SocialNetwork.repositories.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetails implements UserDetailsService {
    private final CustomUserRepository customUserRepository;

    @Autowired
    public CustomUserDetails(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser customUser = customUserRepository.findByUsername(username);
        if(customUser == null) {
            throw new UsernameNotFoundException("User does not exist");
        }

        User user = new User(
                customUser.getUsername(),
                customUser.getPassword(),
                convertRoles(customUser.getCustomRoles())
        );
        return user;
    }

    private Set<SimpleGrantedAuthority> convertRoles(Set<CustomRole> inRoles) {
        return inRoles.stream()
                .map(customRole -> new SimpleGrantedAuthority(customRole.getRoleName()))
                .collect(Collectors.toSet());
    }
}
