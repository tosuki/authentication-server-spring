package com.authservice.http.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authservice.core.dto.AuthenticateUserDTO;
import com.authservice.core.dto.RegisterUserDTO;
import com.authservice.core.io.AuthError;
import com.authservice.core.io.Logger;
import com.authservice.core.model.EmailConfirmation;
import com.authservice.core.model.PassportValidation;
import com.authservice.core.usecase.AuthUsecase;
import com.authservice.http.dto.AuthHttpResponseDTO;
import com.authservice.http.dto.AuthenticateUserResponseDTO;
import com.authservice.http.dto.ConfirmEmailResponseDTO;
import com.authservice.http.dto.RegisterUserResponseDTO;
import com.authservice.http.dto.ValidationPassportResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
            EmailConfirmation emailConfirmation = authUsecase.register(data);

            return RegisterUserResponseDTO.authorize(emailConfirmation.getUser().getEmail());
        } catch (AuthError.EmailConflict emailConflict) {
            return RegisterUserResponseDTO.emailConflict(emailConflict.getEmail());
        } catch (Exception e) {
            return RegisterUserResponseDTO.error(e);
        }
    }

    @GetMapping("/confirmEmail/{confirmationCode}")
    public ResponseEntity<Object> confirmEmail(@PathVariable String confirmationCode) {
        Logger.warn("The confirmation code is %s", confirmationCode);
        try {
            String passport = authUsecase.confirmEmail(confirmationCode);

            return ConfirmEmailResponseDTO.authorize(passport);
        } catch (AuthError.InvalidConfirmationCode invalidConfirmationCode) {
            return ConfirmEmailResponseDTO.invalid();
        } catch (Exception e) {
            return ConfirmEmailResponseDTO.error(e);
        }
    }
    

    @PostMapping("/validate-token")
    public ResponseEntity<Object> validateToken(@RequestBody String passport) {
        try {
            if (passport == null || passport.isEmpty()) {
                return ValidationPassportResponseDTO.lacking();
            }

            PassportValidation passportValidation = authUsecase.validatePassport(passport);

            return ValidationPassportResponseDTO.sucess(passportValidation);
        } catch (Exception e) {
            return ValidationPassportResponseDTO.error(e);
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
