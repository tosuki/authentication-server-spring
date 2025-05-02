package com.authservice.core.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Session {
    public int id;
    public String name;
    public String email;
    public String password;
    public long expiresAt;
    public long issuedAt;
}
