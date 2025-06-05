package com.authservice.http.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.authservice.core.model.EmailConfirmation;

public class RegisterUserResponseDTO extends AuthHttpResponseDTO<String> {
    public RegisterUserResponseDTO(boolean ok, String message, String data) {
        super(ok, message, data);
    }

    public static ResponseEntity<Object> emailConflict(String email) {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(RegisterUserResponseDTO.builder()
                .ok(false)
                .message(email + " is occupied")
                .data(null)
                .build()
            );
    }

    public static ResponseEntity<Object> authorize(String email) {
        return ResponseEntity
            .status(HttpStatus.ACCEPTED)
            .body(RegisterUserResponseDTO.builder()
                .ok(true)
                .message("Authorized")
                .data(email)
                .build()
            );
    }
}
