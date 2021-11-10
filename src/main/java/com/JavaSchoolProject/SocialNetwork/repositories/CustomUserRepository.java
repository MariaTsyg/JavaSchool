package com.JavaSchoolProject.SocialNetwork.repositories;

import com.JavaSchoolProject.SocialNetwork.entities.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {
    public List<CustomUser> findAllByFirstNameAndLastName(String firstName, String lastName);
    public CustomUser findByAccountId(String accountId);
    public CustomUser findByUsername(String username);
    public CustomUser findFirstById(Long id);
    public List<CustomUser> findAllByUsernameContains(String filter);
    public List<CustomUser> findAllByFirstNameAndLastNameContains(String filterName, String filterLastName);
}
