package com.socialmedia.repository;

import com.socialmedia.repository.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IUserProfileRepository extends JpaRepository<UserProfile, Long> {


    Optional<UserProfile> findOptionalByAuthid(Long id);

    Optional<UserProfile> findOptionalByUsername(String username);

    Optional<UserProfile> findOptionalByUsernameEqualsIgnoreCase(String username);

    @Query("select u from UserProfile as u where u.status='ACTIVE'")
    List<UserProfile> getActiveProfile();
}
