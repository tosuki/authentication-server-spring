package com.authservice.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authservice.core.model.User;

public interface UserRepository extends JpaRepository<User, Long> {}