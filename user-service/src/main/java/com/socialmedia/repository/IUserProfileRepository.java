package com.socialmedia.repository;

import com.socialmedia.repository.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserProfileRepository extends JpaRepository<UserProfile, Long> {


    Optional<UserProfile> findOptionalByAuthid(Long id);
}
