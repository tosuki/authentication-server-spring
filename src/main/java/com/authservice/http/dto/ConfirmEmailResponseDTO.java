package com.authservice.http.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ConfirmEmailResponseDTO extends AuthHttpResponseDTO <String> {
    public ConfirmEmailResponseDTO(boolean ok, String message, String data) {
        super(ok, message, data);
    }

    public static ResponseEntity<Object> authorize(String passport) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(AuthHttpResponseDTO.builder()
                .ok(true)
                .message("Registration successful")
                .data(passport)
                .build()
            );
    }

    public static ResponseEntity<Object> invalid() {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(AuthHttpResponseDTO.builder()
                .ok(false)
                .message("Invalid confirmation code")
                .data(null)
                .build()
            );
    }
}
