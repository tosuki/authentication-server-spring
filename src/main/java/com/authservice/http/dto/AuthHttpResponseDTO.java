package com.authservice.http.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.authservice.core.io.Logger;

import lombok.Builder;

@Builder
public class AuthHttpResponseDTO <T> {
    public boolean ok;
    public String message;
    public T data;

    public static ResponseEntity<Object> ok(boolean isOk) {
        return ResponseEntity.ok(AuthHttpResponseDTO.builder()
                .ok(isOk)
                .message("pong!")
                .build()
        );
    }

    public static ResponseEntity<Object> lacking() {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(RegisterUserResponseDTO.builder()
                .ok(false)
                .message("Bad Request")
                .data(null)
                .build()
            );
    }

    public static ResponseEntity<Object> error(Exception exception) {
        Logger.error(exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(AuthHttpResponseDTO.builder()
                .ok(false)
                .message("internal-server-error")
                .build()
        );
    }
}
