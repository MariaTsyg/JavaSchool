package com.JavaSchoolProject.SocialNetwork.repositories;

import com.JavaSchoolProject.SocialNetwork.entities.FollowingDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowingRepository extends JpaRepository<FollowingDTO, Long> {
    public boolean existsByFollowerIdAndUserId(Long follower_id, Long user_id);
    public List<FollowingDTO> findAllByFollowerId(Long follower_id);
}
