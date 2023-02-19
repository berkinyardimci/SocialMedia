package com.socialmedia.repository;

import com.socialmedia.repository.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserProfileRepository extends JpaRepository<UserProfile, Long> {

}
