package com.authservice;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.authservice.core.io.AuthError.EmailConflict;
import com.authservice.core.io.AuthError.WrongCredentials;
import com.authservice.core.usecase.AuthUsecase;
import com.authservice.domain.ports.BCryptPasswordEncoder;
import com.authservice.mocked.MockedUserRepositoryImpl;

public class AuthUsecaseTest {
    private final AuthUsecase authUsecase;

    private final String testEmail = "okaabe2006@gmail.com";
    public final String testPassword = "P@ssw0rd!";

    public AuthUsecaseTest() {
        this.authUsecase = new AuthUsecase(new MockedUserRepositoryImpl(), new BCryptPasswordEncoder());
    }

    public String registerUser() {
        return authUsecase.register("tester", testEmail, testPassword);
    }

    @Test
    @DisplayName("Should return a token (authenticate)")
    public void shouldReturnATokenAuthenticate() {
        registerUser();

        String passport = authUsecase.authenticate(testEmail, testPassword);
        assertNotNull(passport);
    }

    @Test
    @DisplayName("Should fail on wrong credentials")
    public void shouldFailOnWrongCredentials() {
        try {
            authUsecase.authenticate(testEmail, "123");
            fail("Should have threw a wrong credentials exception!");
        } catch (WrongCredentials wrongCredentials) {
        } catch (Exception exception) {
            fail("Should have threw a wrong credentials exception, instead of " + exception.getMessage());
        } 
    }

    @Test
    @DisplayName("Should register the user and return a token")
    public void shouldRegisterTheUserAndReturnAToken() {
        assertNotNull(registerUser());
    }

    @Test
    @DisplayName("Should fail on email conflict")
    public void shouldFailOnEmailConflict() {
        registerUser();

        try {
            registerUser();
            fail("Should have threw an email conflict exception");
        } catch (EmailConflict emailConflict) {
        } catch (Exception exception) {
            fail("Should have threw an email conflict exception instead of " + exception.getMessage());
        }
    }
}