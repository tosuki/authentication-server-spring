package com.authservice;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.authservice.core.dto.RegisterUserDTO;
import com.authservice.core.io.AuthError.EmailConflict;
import com.authservice.core.io.AuthError.WrongCredentials;
import com.authservice.core.model.User;
import com.authservice.core.repository.UserRepository;
import com.authservice.core.usecase.AuthUsecase;
import com.authservice.domain.ports.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AuthUsecaseTest {
    private UserRepository userRepository;
    private AuthUsecase authUsecase;

    @BeforeEach()
    public void beforeEach() {
        this.authUsecase = new AuthUsecase(userRepository, new BCryptPasswordEncoder(), null);
    }

    @BeforeAll
    public static void beforeAll() {
        MockitoAnnotations.openMocks(AuthUsecaseTest.class);
    }

    private final String testEmail = "okaabe2006@gmail.com";
    public final String testPassword = "P@ssw0rd!";

    public String registerUser() {
        when(userRepository.save(any())).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(UUID.randomUUID().toString());

            return user;
        });
    
        return authUsecase.register(RegisterUserDTO.builder()
            .email(testEmail)
            .password(testPassword)
            .name("Tester")
            .build()
        );
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