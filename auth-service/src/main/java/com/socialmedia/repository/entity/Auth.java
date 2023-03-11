package com.socialmedia.repository.entity;

import com.socialmedia.repository.enums.Roles;
import com.socialmedia.repository.enums.Status;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Auth implements Serializable {
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

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.PENDING;

    private String activatedCode;

}
