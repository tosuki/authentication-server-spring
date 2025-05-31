package com.authservice.domain.ports;

import com.authservice.core.model.Session;
import com.authservice.core.model.User;
import com.authservice.core.ports.PassportEncoder;

public class JWTPassportEncoder implements PassportEncoder {

    @Override
    public String encode(User user) {
        throw new UnsupportedOperationException("Unimplemented method 'encode'");
    }

    @Override
    public Session decode(String passport) {
        throw new UnsupportedOperationException("Unimplemented method 'decode'");
    }

    @Override
    public boolean isExpired(Session session) {
        throw new UnsupportedOperationException("Unimplemented method 'isExpired'");
    }
    
}
