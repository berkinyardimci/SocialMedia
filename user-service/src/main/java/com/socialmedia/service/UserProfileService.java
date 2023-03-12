package com.socialmedia.service;

import com.socialmedia.dto.request.ActivateCodeRequest;
import com.socialmedia.dto.request.NewCreateUserDto;
import com.socialmedia.dto.request.UpdateRequestDto;
import com.socialmedia.dto.response.RoleResponseDto;
import com.socialmedia.dto.response.UserProfileRedisResponseDto;
import com.socialmedia.exception.ErrorType;
import com.socialmedia.exception.UserManagerException;
import com.socialmedia.manager.IAuthManager;
import com.socialmedia.mapper.IUserMapper;
import com.socialmedia.repository.IUserProfileRepository;
import com.socialmedia.repository.entity.UserProfile;
import com.socialmedia.repository.enums.Status;
import com.socialmedia.utility.JwtTokenManager;
import com.socialmedia.utility.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile, Long> {

    private final IUserProfileRepository userProfileRepository;
    private final JwtTokenManager jwtTokenManager;

    @Autowired
    private CacheManager cacheManager;

    private final IAuthManager authManager;

    public UserProfileService(IUserProfileRepository userProfileRepository, JwtTokenManager jwtTokenManager, IAuthManager authManager) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.authManager = authManager;
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

    public boolean updateUser(UpdateRequestDto dto) {

        Optional<Long> authid = jwtTokenManager.getUserId(dto.getToken());
        if (authid.isPresent()) {
            Optional<UserProfile> userProfileDb = userProfileRepository.findOptionalByAuthid(authid.get());
            if (userProfileDb.isPresent()) {

                userProfileDb.get().setEmail(dto.getEmail());
                userProfileDb.get().setAddress(dto.getAddress());
                userProfileDb.get().setAbout(dto.getAbout());
                userProfileDb.get().setName(dto.getName());
                userProfileDb.get().setUsername(dto.getUsername());
                userProfileDb.get().setPhone(dto.getPhone());
                userProfileDb.get().setPhone(dto.getPhoto());
                userProfileDb.get().setUpdated(System.currentTimeMillis());
                save(userProfileDb.get());
                return true;
            } else {
                throw new UserManagerException(ErrorType.INVALID_TOKEN);

            }
        } else {
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
    }
    public boolean updateUserforRedis(UpdateRequestDto dto) {

        Optional<Long> authid = jwtTokenManager.getUserId(dto.getToken());
        if (authid.isPresent()) {
            Optional<UserProfile> userProfileDb = userProfileRepository.findOptionalByAuthid(authid.get());
            if (userProfileDb.isPresent()) {
                cacheManager.getCache("findbuysername").evict(userProfileDb.get().getUsername());

                userProfileDb.get().setEmail(dto.getEmail());
                userProfileDb.get().setAddress(dto.getAddress());
                userProfileDb.get().setAbout(dto.getAbout());
                userProfileDb.get().setName(dto.getName());
                userProfileDb.get().setUsername(dto.getUsername());
                userProfileDb.get().setPhone(dto.getPhone());
                userProfileDb.get().setPhone(dto.getPhoto());
                userProfileDb.get().setUpdated(System.currentTimeMillis());
                save(userProfileDb.get());
                return true;
            } else {
                throw new UserManagerException(ErrorType.INVALID_TOKEN);

            }
        } else {
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
    }

    public Boolean activateStatus(Long authid) {
        Optional<UserProfile> userProfile = userProfileRepository.findOptionalByAuthid(authid);
        if (userProfile.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setStatus(Status.ACTIVE);
        save(userProfile.get());
        return true;
    }

    @Cacheable(value = "findbyusername",key = "#username.toUpperCase()")
    public UserProfileRedisResponseDto fidByUsername(String username) {
        Optional<UserProfile> userProfile = userProfileRepository.findOptionalByUsernameEqualsIgnoreCase(username);
        if (userProfile.isPresent()) {
            return IUserMapper.INSTANCE.toUserProfileRedisResponseDto(userProfile.get());
        }else{
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
    }

    @Cacheable(value= "findactiveprofile")
    public List<UserProfile> findAllActiveProfile() {
        return userProfileRepository.getActiveProfile();

    }

    @Cacheable(value= "findbyrole", key= "#role.toUpperCase()")
    public List<RoleResponseDto> findByrole(String role) {
        return authManager.findAllByRoles(role).getBody();
    }
}
