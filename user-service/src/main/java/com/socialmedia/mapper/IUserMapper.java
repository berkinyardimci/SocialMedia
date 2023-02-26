package com.socialmedia.mapper;

import com.socialmedia.dto.request.NewCreateUserDto;
import com.socialmedia.dto.request.UpdateRequestDto;
import com.socialmedia.repository.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface IUserMapper {
    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);


    UserProfile toUserProfile(final NewCreateUserDto dto);

    UserProfile toUserProfile(final UpdateRequestDto dto);
}
