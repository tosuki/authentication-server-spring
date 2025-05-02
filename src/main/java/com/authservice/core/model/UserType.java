package com.authservice.core.model;

public enum UserType {
    NORMAL(1),
    REVIEWER(2),
    ADMINISTRATOR(2);

    private final int id;
    
    public int getId() {
        return id;
    }

    UserType(int id) {
        this.id = id;
    }
}
