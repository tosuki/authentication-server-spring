package com.authservice.core.io;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class AuthError extends CoreError {
    public AuthError(String level, String message) {
        super("Authentication", level, message);
    }

    @ResponseStatus(code = HttpStatus.CONFLICT)
    public static class EmailConflict extends AuthError {
        public EmailConflict(String level, String email) {
            super(level, String.format("The email %s is occupied!", email));
        } 
    }

    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public static class WrongCredentials extends AuthError {
        public WrongCredentials(String level) {
            super(level, "Wrong credentials");
        }
    }
}
