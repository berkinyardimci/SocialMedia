package com.socialmedia.controller;

import com.socialmedia.repository.entity.UserProfile;
import com.socialmedia.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import static com.socialmedia.constant.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ELASTIC)
public class UserProfileController {

    private final UserProfileService userProfileService;
    @GetMapping(GETALL)
    public Iterable<UserProfile> findAll(){
        return userProfileService.findAll();
    }


}
