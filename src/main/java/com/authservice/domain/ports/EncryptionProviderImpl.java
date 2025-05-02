package com.authservice.domain.ports;

import org.springframework.stereotype.Service;

import com.authservice.core.port.EncryptionProvider;

@Service
public class EncryptionProviderImpl implements EncryptionProvider {
    @Override
    public String encode(String value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'encode'");
    }

    @Override
    public boolean compare(String encoded, String decoded) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compare'");
    }
    
}
