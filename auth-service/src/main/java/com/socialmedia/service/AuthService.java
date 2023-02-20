package com.socialmedia.service;

import com.socialmedia.dto.request.ActivateCodeRequest;
import com.socialmedia.dto.request.LoginRequestDto;
import com.socialmedia.dto.request.NewCreateUserDto;
import com.socialmedia.dto.request.RegisterRequestDto;
import com.socialmedia.dto.response.LoginResponseDto;
import com.socialmedia.dto.response.RegisterResponseDto;
import com.socialmedia.exception.AuthManagerException;
import com.socialmedia.exception.ErrorType;
import com.socialmedia.manager.IUserManager;
import com.socialmedia.mapper.IAuthMapper;
import com.socialmedia.repository.IAuthRepository;
import com.socialmedia.repository.entity.Auth;
import com.socialmedia.repository.enums.Roles;
import com.socialmedia.repository.enums.Status;
import com.socialmedia.utility.CodeGenerator;
import com.socialmedia.utility.JwtTokenManager;
import com.socialmedia.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService extends ServiceManager {

    private final IAuthRepository authRepository;
    private final IUserManager userManager;
    private JwtTokenManager jwtTokenManager;

    public AuthService(IAuthRepository authRepository, IUserManager userManager,JwtTokenManager jwtTokenManager) {
        super(authRepository);
        this.authRepository = authRepository;
        this.userManager = userManager;
        this.jwtTokenManager = jwtTokenManager;
    }

    public RegisterResponseDto register(RegisterRequestDto dto) {

        Auth auth = IAuthMapper.INSTANCE.toAuth(dto);

        if (userIsExist(dto.getUsername())) {
            throw new AuthManagerException(ErrorType.USERNAME_DUPLICATE);
        } else {
            if (dto.getAdminCode() != null && dto.getAdminCode().equals("admin")) {
                auth.setRole(Roles.ADMIN);
            }
            try {
                auth.setActivatedCode(CodeGenerator.generateCode(UUID.randomUUID().toString()));
                save(auth);
                userManager.createUser(NewCreateUserDto.builder()
                        .authid(auth.getId())
                        .email(auth.getEmail())
                        .username(auth.getUsername())
                        .build());
                return IAuthMapper.INSTANCE.toRegisterResponseDto(auth);
            } catch (Exception ex) {
                throw new AuthManagerException(ErrorType.USER_NOT_CREATED);
            }

        }

    }

    private boolean userIsExist(String username) {
        return authRepository.existUserName(username);
    }

    public Optional<LoginResponseDto> login(LoginRequestDto dto) {
        Optional<Auth> auth = authRepository.findOptionalByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if (auth.isPresent()) {
            LoginResponseDto loginResponseDto = IAuthMapper.INSTANCE.toLoginResponseDto(auth.get());
            String token = jwtTokenManager.createToken(loginResponseDto.getId());
            loginResponseDto.setToken(token);

            return Optional.of(loginResponseDto);
        } else {
            throw new AuthManagerException(ErrorType.LOGIN_ERROR_WRONG);
        }

    }

    public boolean activateStatus(ActivateCodeRequest dto) {
        Optional<Auth> auth = authRepository.findById(dto.getId());
        if (auth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (auth.get().getActivatedCode().equals(dto.getActivatedCode())) {
            auth.get().setStatus(Status.ACTIVE);
            userManager.activateStatus(dto);
            save(auth.get());
            return true;
        }
        throw new AuthManagerException(ErrorType.INVALID_ACTİVATE_CODE);
    }
}
