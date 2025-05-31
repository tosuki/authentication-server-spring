package com.authservice.core.io;

public class AuthError extends CoreError {
    public AuthError(String level, String message) {
        super("Authentication", level, message);
    }

    public static class EmailConflict extends AuthError {
        public EmailConflict(String level, String email) {
            super(level, String.format("The email %s is occupied!", email));
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
}
