package com.JavaSchoolProject.SocialNetwork.repositories;

import com.JavaSchoolProject.SocialNetwork.entities.PostDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostDTO, Long> {
    public long countByUsername(String username);
    public List<PostDTO> findAllByUsernameOrderByDateDesc(String username);
    public List<PostDTO> findAllByUsername(String username);
}
