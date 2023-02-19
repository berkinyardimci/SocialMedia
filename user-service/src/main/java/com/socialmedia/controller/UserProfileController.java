package com.socialmedia.controller;

import com.socialmedia.dto.request.NewCreateUserDto;
import com.socialmedia.exception.ErrorType;
import com.socialmedia.exception.UserManagerException;
import com.socialmedia.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping("/create")
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
}
