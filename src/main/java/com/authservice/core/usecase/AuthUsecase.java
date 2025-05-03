package com.authservice.core.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authservice.core.model.User;
import com.authservice.core.repository.UserRepository;

@Service
public class AuthUsecase {
    @Autowired
    private UserRepository userRepository;

    public void authenticate(String email, String password) {
    }
}
