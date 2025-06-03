package com.authservice.http.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authservice.core.dto.AuthenticateUserDTO;
import com.authservice.core.dto.RegisterUserDTO;
import com.authservice.core.io.AuthError;
import com.authservice.core.usecase.AuthUsecase;
import com.authservice.http.dto.AuthHttpResponseDTO;
import com.authservice.http.dto.AuthenticateUserResponseDTO;
import com.authservice.http.dto.RegisterUserResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthUsecase authUsecase;

    @GetMapping("/ok")
    public ResponseEntity<Object> isOk() {
        return AuthHttpResponseDTO.ok(true);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody RegisterUserDTO data) {
        if (!data.validate()) {
            return RegisterUserResponseDTO.lacking();
        }

        try {
            String passport = authUsecase.register(data);

            return RegisterUserResponseDTO.authorize(passport);
        } catch (AuthError.EmailConflict emailConflict) {
            return RegisterUserResponseDTO.emailConflict(emailConflict.getEmail());
        } catch (Exception e) {
            return RegisterUserResponseDTO.error(e);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticateUserDTO data) {
        try {
            if (!data.validate()) {
                return AuthHttpResponseDTO.lacking();
            }

            String passport = authUsecase.authenticate(data);

            return AuthenticateUserResponseDTO.authorize(passport);
        } catch (AuthError.WrongCredentials wrongCredentials) {
            return AuthenticateUserResponseDTO.wrongCredentials();
        } catch (Exception e) {
            return AuthHttpResponseDTO.error(e);
        }
    }
    
}
