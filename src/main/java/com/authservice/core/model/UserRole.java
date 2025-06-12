package com.authservice.core.model;

import com.authservice.core.io.AuthError;

public enum UserRole {
    ADMINISTRATOR(0),
    NORMAL(1);

    private final int value;

    UserRole(int value) {
        this.value = value;
    }

    public int getRoleID() {
        return value;
    }

    public static UserRole fromID(int id) {
        for (UserRole role : UserRole.values()) {
            if (role.getRoleID() == id) {
                return role;
            }
        }

        throw new AuthError.InvalidRoleID();
    }
}
