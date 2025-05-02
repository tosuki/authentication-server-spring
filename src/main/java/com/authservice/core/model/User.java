package com.authservice.core.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class User {
    public int id;
    public String name;
    public String email;
    public String password;
    public UserType userType;
}
