package com.socialmedia.service;

import com.socialmedia.dto.request.NewCreateUserDto;
import com.socialmedia.mapper.IUserMapper;
import com.socialmedia.repository.IUserProfileRepository;
import com.socialmedia.repository.entity.UserProfile;
import com.socialmedia.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService extends ServiceManager<UserProfile,Long> {

    private final IUserProfileRepository userProfileRepository;

    public UserProfileService(IUserProfileRepository userProfileRepository) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
    }

    public UserProfile createUser(NewCreateUserDto dto){

        return userProfileRepository.save(IUserMapper.INSTANCE.toUserProfile(dto));
    }
}
