package com.authservice.http.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.authservice.core.model.PassportValidation;

public class ValidationPassportResponseDTO extends AuthHttpResponseDTO<PassportValidation> {
    public ValidationPassportResponseDTO(boolean ok, String message, PassportValidation data) {
        super(ok, message, data);
    }

    public static ResponseEntity<Object> sucess(PassportValidation passportValidation) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ValidationPassportResponseDTO.builder()
                .ok(true)
                .message("passport is valid!")
                .data(passportValidation)
                .build()
            );
    }
}
