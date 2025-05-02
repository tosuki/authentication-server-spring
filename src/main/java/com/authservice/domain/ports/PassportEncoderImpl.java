package com.authservice.domain.ports;

import org.springframework.stereotype.Service;

import com.authservice.core.model.Session;
import com.authservice.core.model.User;
import com.authservice.core.port.PassportEncoder;

@Service
public class PassportEncoderImpl implements PassportEncoder {
    @Override
    public String encode(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'encode'");
    }

    @Override
    public Session decode(Session session) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'decode'");
    }
    
}
