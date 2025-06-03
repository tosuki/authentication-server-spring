package com.authservice.core.model;

public enum PassportExpiration {
    EXPIRED("expired"),
    VALID("valid"),
    INVALID("invalid");

    private final String value;

    private PassportExpiration(String value) {
        this.value = value;
    }

    public static PassportExpiration fromString(String value) {
        for (PassportExpiration expiration : PassportExpiration.values()) {
            if (expiration.value.equalsIgnoreCase(value)) {
                return expiration;
            }
        }
        return INVALID; // Default to INVALID if no match found
    }

    public String toString() {
        return value;
    }
}
