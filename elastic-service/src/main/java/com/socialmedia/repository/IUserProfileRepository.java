package com.socialmedia.repository;

import com.socialmedia.repository.entity.UserProfile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


import java.util.List;
import java.util.Optional;

public interface IUserProfileRepository extends ElasticsearchRepository<UserProfile, Long> {


    Optional<UserProfile> findOptionalByAuthid(Long id);

    Optional<UserProfile> findOptionalByUsername(String username);

    Optional<UserProfile> findOptionalByUsernameEqualsIgnoreCase(String username);

}
