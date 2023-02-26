package com.socialmedia.dto.request;

import com.socialmedia.repository.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRequestDto {
    String token;
    String username;
    String name;
    String email;
    String phone;
    String photo;
    String address;
    String about;
}
