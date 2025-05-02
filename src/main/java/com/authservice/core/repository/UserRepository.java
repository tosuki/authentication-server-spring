package com.authservice.core.repository;

import com.authservice.core.model.User;

public interface UserRepository {
    User add(String name, String email, String password);
    User get(String email);
    User get(int id);
}
