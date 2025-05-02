package com.authservice.core.io;

public class Logger {
    private static final String RESET = "\u001B[0m\n";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";

    public static void log(String color, String format, Object ...args) {
        System.out.printf(color + format + RESET , args);
    }

    public static void warn(String format, Object ...args) {
        log(YELLOW, format, args);
    }

    public static void info(String format, Object ...args) {
        log(GREEN, format, args);
    }

    public static void error(Exception error) {
        error(error.getMessage());
    }

    public static void error(String format, Object ...args) {
        log(RED, format, args);
    }
}