package com.authservice.core.io;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class IllegalError extends CoreError {
    public IllegalError(String level, String message) {
        super("Illegal", level, message);
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public static class EncryptionError extends IllegalError {
        public EncryptionError(String level, Exception e) {
            super(level, String.format("Failed to handle the encryption of the password due to %s\n", e.getMessage()));
        }        
    }
}
