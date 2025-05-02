package com.authservice.core.port;

public interface EncryptionProvider {
    String encode(String value);
    boolean compare(String encoded, String decoded);
}
