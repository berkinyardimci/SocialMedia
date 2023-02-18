package com.socialmedia.service;

import com.socialmedia.dto.request.LoginRequestDto;
import com.socialmedia.dto.request.RegisterRequestDto;
import com.socialmedia.dto.response.LoginResponseDto;
import com.socialmedia.exception.AuthManagerException;
import com.socialmedia.exception.ErrorType;
import com.socialmedia.mapper.IAuthMapper;
import com.socialmedia.repository.IAuthRepository;
import com.socialmedia.repository.entity.Auth;
import com.socialmedia.repository.enums.Roles;
import com.socialmedia.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager {

    private final IAuthRepository authRepository;

    public AuthService(IAuthRepository authRepository) {
        super(authRepository);
        this.authRepository = authRepository;
    }

    public Auth register(RegisterRequestDto dto) {

        Auth auth = IAuthMapper.INSTANCE.toAuth(dto);

        if (userIsExist(dto.getUsername())) {
            throw new AuthManagerException(ErrorType.USERNAME_DUPLICATE);
        } else {
            if (dto.getAdminCode() != null && dto.getAdminCode().equals("admin")) {
                auth.setRole(Roles.ADMIN);
            }
            try {
                save(auth);
                return auth;
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

            return Optional.of(IAuthMapper.INSTANCE.toLoginResponseDto(auth.get()));
        } else {
            throw new AuthManagerException(ErrorType.LOGIN_ERROR_WRONG);
        }

    }
}
