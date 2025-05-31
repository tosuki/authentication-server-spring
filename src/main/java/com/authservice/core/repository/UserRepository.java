package com.authservice.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.authservice.core.model.User;

public interface UserRepository extends JpaRepository<User, String> {
    @Query(
        value = "SELECT * FROM users WHERE email = :email LIMIT 1;",
        nativeQuery = true
    )
    Optional<User> getByEmail(@Param("email") String email);
}