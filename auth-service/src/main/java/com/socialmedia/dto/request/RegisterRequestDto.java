package com.socialmedia.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDto {

    @NotNull
    @NotBlank
    @Size(min =3, max=20, message = "En az 3 en fazla 20 karakter olmalı")
    private String username;
    @NotBlank
    private String password;
    @Email
    @NotBlank
    private String email;
    private String adminCode;

}
