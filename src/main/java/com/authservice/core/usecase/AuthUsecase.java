package com.authservice.core.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authservice.core.ports.PasswordEncoder;
import com.authservice.core.repository.UserRepository;

@Service
public class AuthUsecase {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public AuthUsecase(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(String name, String email, String password) {
        System.out.println(passwordEncoder.encode(password));

        return password;
    }

    public String authenticate(String email, String password) {
        return password;
    }
}
