package com.socialmedia.repository;

import com.socialmedia.repository.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthRepository  extends JpaRepository<Auth,Long> {
}
