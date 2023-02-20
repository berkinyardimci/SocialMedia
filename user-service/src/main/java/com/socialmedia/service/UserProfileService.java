package com.socialmedia.service;

import com.socialmedia.dto.request.ActivateCodeRequest;
import com.socialmedia.dto.request.NewCreateUserDto;
import com.socialmedia.exception.ErrorType;
import com.socialmedia.exception.UserManagerException;
import com.socialmedia.mapper.IUserMapper;
import com.socialmedia.repository.IUserProfileRepository;
import com.socialmedia.repository.entity.UserProfile;
import com.socialmedia.repository.enums.Status;
import com.socialmedia.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile, Long> {

    private final IUserProfileRepository userProfileRepository;

    public UserProfileService(IUserProfileRepository userProfileRepository) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
    }

    public UserProfile createUser(NewCreateUserDto dto) {

        return userProfileRepository.save(IUserMapper.INSTANCE.toUserProfile(dto));
    }

    public Boolean activateStatus(ActivateCodeRequest dto) {

        Optional<UserProfile> userProfile = userProfileRepository.findOptionalByAuthid(dto.getId());
        if (userProfile.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setStatus(Status.ACTIVE);
        save(userProfile.get());
        return true;
    }
}
