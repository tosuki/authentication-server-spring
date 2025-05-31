package com.authservice.core.io;

public class IllegalError extends CoreError {
    public IllegalError(String level, String message) {
        super("Illegal", level, message);
    }

    public static class EncryptionError extends IllegalError {
        public EncryptionError(String level, Exception e) {
            super(level, String.format("Failed to handle the encryption of the password due to %s\n", e.getMessage()));
        }        
    }

    public static class UnknownError extends IllegalError {
        public UnknownError(Exception exception, String level) {
            super(level, String.format("An unknown exception was thrown: %s", exception));
        }
    }

    public static class JSONSerializationError extends IllegalError {
        public JSONSerializationError(String level, Exception e) {
            super(level, String.format("Failed to serialize the object to JSON due to %s\n", e.getMessage()));
        }
    }
    
    public static class JSONDeserializationError extends IllegalError {
        public JSONDeserializationError(String level, Exception e) {
            super(level, String.format("Failed to deserialize the object from JSON due to %s\n", e.getMessage()));
        }
    }
}
