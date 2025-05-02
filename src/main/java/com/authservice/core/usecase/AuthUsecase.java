package com.authservice.core.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.authservice.core.io.AuthError;
import com.authservice.core.io.CoreError;
import com.authservice.core.model.User;
import com.authservice.core.port.EncryptionProvider;
import com.authservice.core.port.PassportEncoder;
import com.authservice.core.repository.UserRepository;

@Component
public class AuthUsecase {
    @Autowired private UserRepository userRepository;
    @Autowired private PassportEncoder passportEncoder;
    @Autowired private EncryptionProvider encryptionProvider;

    /**
     * Authenticate the user
     * returns a passport
     * 
     * As it was mentioned in the register method, this should use the implementations of the ports and repository to handle the authentication!
     */
    public String authenticate(String email, String password) {
        try {
            User user = userRepository.get(email);

            if (user == null || !encryptionProvider.compare(user.password, password)) {
                throw new AuthError.WrongCredentials("auth usecase - authenticate");
            }

            String passport = passportEncoder.encode(user);

            return passport;
        } catch (CoreError error) {
            throw error;
        }
    }

    /**
     * Register the user
     * returns a JWT
     * 
     * The idea is to create providers for aws cognito and consume them here
     */
    public String register(String name, String email, String password) {
        try {
            String encrypted = encryptionProvider.encode(password);
            User user = userRepository.add(name, email, encrypted);

            String passport = passportEncoder.encode(user);

            return passport;
        } catch (CoreError error) {
            throw error;
        }
    }

    public String rewoke(String passport) {
        return passport;
    }
}
