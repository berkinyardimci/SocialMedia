package com.socialmedia.repository.entity;

import com.socialmedia.repository.enums.Roles;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String username;
    private  String password;
    private String email;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Roles role=Roles.USER;


}
