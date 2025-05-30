package com.authservice;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.authservice.core.ports.PasswordEncoder;
import com.authservice.domain.ports.BCryptPasswordEncoder;

public class PasswordEncoderTest {
    private final PasswordEncoder passwordEncoder;

    public PasswordEncoderTest() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    @DisplayName("Should return a hash")
    public void shouldReturnAHash() {
        String hash = passwordEncoder.encode("hello");

        assertNotNull(hash, "Should have returned a hash instead of null");
    }

    @Test
    @DisplayName("Should return true to the comparison between the hash and the password")
    public void shouldReturnTrue() {
        String hash = passwordEncoder.encode("hello");

        assertTrue(passwordEncoder.match(hash, "hello"), "Should have returned true");
    }

    @Test
    @DisplayName("Should return false to the comparison")
    public void shouldReturnFalse() {
        String hash = passwordEncoder.encode("hello");
        assertFalse(passwordEncoder.match(hash, "aa"), "Should have returned false");
    }
}
