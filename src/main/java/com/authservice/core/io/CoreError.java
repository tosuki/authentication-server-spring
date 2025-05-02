package com.authservice.core.io;

public class CoreError extends Error {
    public CoreError(String category, String level, String message) {
        super("[" + category + "]" + message + " - " + level);
    }
}
