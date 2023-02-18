package com.socialmedia.controller;

import com.socialmedia.dto.request.RegisterRequestDto;
import com.socialmedia.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/register")
    public ResponseEntity<String> register(RegisterRequestDto dto){
        return ResponseEntity.ok("Deneme");
    }
}
