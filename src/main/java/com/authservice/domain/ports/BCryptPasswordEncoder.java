package com.authservice.domain.ports;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

import com.authservice.core.io.IllegalError;
import com.authservice.core.ports.PasswordEncoder;

@Component
public class BCryptPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(String password) {
        try {
            return BCrypt.hashpw(password, BCrypt.gensalt());
        } catch (Exception e) {
            throw new IllegalError.EncryptionError("bcrypt password encoder - encode", e);
        }
    }

    @Override
    public boolean match(String encoded, String password) {
        return BCrypt.checkpw(password, encoded);
    }
}
