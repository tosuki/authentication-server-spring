package com.authservice.core.io;

import lombok.Getter;

public class AuthError extends CoreError {
    public AuthError(String level, String message) {
        super("Authentication", level, message);
    }

    @Getter
    public static class EmailConflict extends AuthError {
        private final String email;
    
        public EmailConflict(String level, String email) {
            super(level, String.format("The email %s is occupied!", email));
            
            this.email = email;
        } 
    }

    public static class WrongCredentials extends AuthError {
        public WrongCredentials(String level) {
            super(level, "Wrong credentials");
        }
    }

    public static class InvalidPassport extends AuthError {
        public InvalidPassport(String level) {
            super(level, "Invalid passport");
        }
    }

    public static class ExpiredToken extends AuthError {
        public ExpiredToken(String level) {
            super(level, "Expired token");
        }
    }

    public static class InvalidConfirmationCode extends AuthError {
        public InvalidConfirmationCode(String level) {
            super(level, "Invalid confirmation code");
        }
    }

    public static class InvalidRoleID extends AuthError {
        public InvalidRoleID() {
            super("", "Invalid role ID");
        }
    }
}
