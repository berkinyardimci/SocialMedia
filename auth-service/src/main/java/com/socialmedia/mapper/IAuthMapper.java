package com.socialmedia.mapper;

import com.socialmedia.dto.request.LoginRequestDto;
import com.socialmedia.dto.request.NewCreateUserDto;
import com.socialmedia.dto.request.RegisterRequestDto;
import com.socialmedia.dto.response.LoginResponseDto;
import com.socialmedia.dto.response.RegisterResponseDto;
import com.socialmedia.dto.response.RoleResponseDto;
import com.socialmedia.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {

    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);

    RegisterRequestDto toRegisterRequestDto(final Auth auth);

    Auth toAuth(final RegisterRequestDto dto);

    Auth toAuth(final LoginRequestDto dto);

    LoginResponseDto toLoginResponseDto(final Auth auth);

    RegisterResponseDto toRegisterResponseDto(final Auth auth);

    NewCreateUserDto toNewCreateUserDto(final Auth auth);

    RoleResponseDto toRoleResponseDto(final Auth auth);


}
