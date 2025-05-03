package com.authservice.core.ports;

public interface PasswordEncoder {
    public String encode(String password);
    public boolean match(String encoded, String password);
}

