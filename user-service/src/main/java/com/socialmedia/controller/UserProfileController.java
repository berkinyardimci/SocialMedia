package com.socialmedia.controller;

import static com.socialmedia.constant.ApiUrls .*;

import com.socialmedia.dto.request.ActivateCodeRequest;
import com.socialmedia.dto.request.NewCreateUserDto;
import com.socialmedia.dto.request.UpdateRequestDto;
import com.socialmedia.dto.response.RoleResponseDto;
import com.socialmedia.dto.response.UserProfileRedisResponseDto;
import com.socialmedia.exception.ErrorType;
import com.socialmedia.exception.UserManagerException;
import com.socialmedia.repository.entity.UserProfile;
import com.socialmedia.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER)
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping(CREATE)
    public ResponseEntity<Boolean> createUser(@RequestBody NewCreateUserDto dto) {
        try {
            if(dto.getEmail() ==null){
                throw new UserManagerException(ErrorType.USER_NOT_CREATED);
            }
            System.out.println(dto.getUsername());
            userProfileService.createUser(dto);
            System.out.println(dto.getUsername());
            return ResponseEntity.ok(true);
        } catch (Exception exception) {
            throw new UserManagerException(ErrorType.USER_NOT_CREATED);
        }
    }

    @PostMapping(ACTIVATESTATUS)
    public ResponseEntity<Boolean> activateStatus(@RequestBody ActivateCodeRequest dto){
        return ResponseEntity.ok(userProfileService.activateStatus(dto));
    }
    @PostMapping(ACTIVATESTATUSBYID)
    public ResponseEntity<Boolean> activateStatus(@PathVariable Long authid){
        return ResponseEntity.ok(userProfileService.activateStatus(authid));
    }

    @PutMapping(UPDATE)
    public ResponseEntity<Boolean> updateProfile(@RequestBody UpdateRequestDto dto){
        return ResponseEntity.ok(userProfileService.updateUser(dto));
    }

    @PutMapping("/updateRedis")
    public ResponseEntity<Boolean> updateProfileforRedis(@RequestBody UpdateRequestDto dto){
        return ResponseEntity.ok(userProfileService.updateUserforRedis(dto));
    }

    @GetMapping(GETALL)
    public ResponseEntity<List<UserProfile>> findAll(){
        return ResponseEntity.ok(userProfileService.findAll());
    }

    @GetMapping("/findbyusername/{username}")
    public ResponseEntity<UserProfileRedisResponseDto> findbyUsername(@PathVariable String username){
        return ResponseEntity.ok(userProfileService.fidByUsername(username));
    }

    @GetMapping("/findallactiveprofile")
    public ResponseEntity<List<UserProfile>> findAllActiveProfile(){
        return ResponseEntity.ok(userProfileService.findAllActiveProfile());
    }

    @GetMapping("/findbyrole")
    public ResponseEntity<List<RoleResponseDto>> findbyRole(String role){
        return ResponseEntity.ok(userProfileService.findByrole(role));
    }
}
