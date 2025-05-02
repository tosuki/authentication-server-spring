package com.authservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class AuthUsecaseTest {
    @Test
    @Order(1)
    public void testRegister() {
        Assertions.assertFalse(true);
    }

    @Test
    @Order(2)
    public void testAuthenticate() {
        Assertions.assertFalse(true);
    }
}
