package com.authservice.http.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AuthenticateUserResponseDTO extends AuthHttpResponseDTO<String> {
    public AuthenticateUserResponseDTO(boolean ok, String message, String data) {
        super(ok, message, data);
    }

    public static ResponseEntity<Object> authorize(String passport) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(AuthenticateUserResponseDTO.builder()
                .ok(true)
                .message("Authorized")
                .data(passport)
                .build()
            );
    }

    public static ResponseEntity<Object> wrongCredentials() {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(AuthenticateUserResponseDTO.builder()
                .ok(false)
                .message("wrong-credentials")
                .data(null)
                .build()
            );
    }
}
