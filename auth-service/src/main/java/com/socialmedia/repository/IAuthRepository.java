package com.socialmedia.repository;

import com.socialmedia.repository.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IAuthRepository  extends JpaRepository<Auth,Long> {
    @Query("select count(a.username)>0 from Auth as a where a.username=?1")
    Boolean existUserName(String username);

    Optional<Auth> findOptionalByUsernameAndPassword(String username, String password);
}
