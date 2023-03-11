package com.socialmedia.controller;

import static com.socialmedia.constant.ApiUrls .*;
import com.socialmedia.dto.request.ActivateCodeRequest;
import com.socialmedia.dto.request.LoginRequestDto;
import com.socialmedia.dto.request.RegisterRequestDto;
import com.socialmedia.dto.response.LoginResponseDto;
import com.socialmedia.dto.response.RegisterResponseDto;
import com.socialmedia.repository.entity.Auth;
import com.socialmedia.service.AuthService;
import com.socialmedia.utility.JwtTokenManager;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
public class AuthController {

    private final AuthService authService;
    private final JwtTokenManager jwtTokenManager;

    @PostMapping(REGISTER)
    @Operation(summary="Register Method")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto dto) {

        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping(LOGIN)
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto).get());
    }

    @PostMapping(ACTIVATESTATUS)
    public ResponseEntity<Boolean> activateStatus(@RequestBody ActivateCodeRequest dto) {
        return ResponseEntity.ok(authService.activateStatus(dto));
    }

    @GetMapping(GETTOKEN)
    public String getToken(Long id){
        return jwtTokenManager.createToken(id);
    }

    @GetMapping(GETIDBYTOKEN)
    public Long getId(String token){
        return jwtTokenManager.getUserId(token).get();
    }

    @GetMapping(GETALLAUTH)
    public ResponseEntity<List<Auth>> findAll(String token){
        return ResponseEntity.ok(authService.findAll());
    }

    @GetMapping("/redis")
    @Cacheable(value = "redis_example")
    public String redisExample(String string){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return string;
    }
}
