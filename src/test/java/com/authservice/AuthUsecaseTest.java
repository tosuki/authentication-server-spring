package com.authservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.authservice.core.dto.RegisterUserDTO;
import com.authservice.core.io.AuthError;
import com.authservice.core.model.User;
import com.authservice.core.ports.PassportEncoder;
import com.authservice.core.ports.PasswordEncoder;
import com.authservice.core.repository.UserRepository;
import com.authservice.core.usecase.AuthUsecase;
import com.authservice.domain.ports.BCryptPasswordEncoder;
import com.authservice.domain.ports.JWTPassportEncoder;

public class AuthUsecaseTest {

    @Mock
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;
    private PassportEncoder passportEncoder;

    private AuthUsecase authUsecase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        passwordEncoder = new BCryptPasswordEncoder(); // Use your domain implementation
        passportEncoder = new JWTPassportEncoder("aa"); // Use your domain implementation
        authUsecase = new AuthUsecase(userRepository, passwordEncoder, passportEncoder);
    }

    @Test
    @DisplayName("Should register a new user successfully")
    public void shouldRegisterNewUserSuccessfully() {
        RegisterUserDTO registerUserDTO = RegisterUserDTO.builder()
            .name("John Doe")
            .email("john.doe@example.com")
            .password("password123")
            .build();

        when(userRepository.getByEmail(registerUserDTO.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(UUID.randomUUID().toString());
            return user;
        });

        String passport = authUsecase.register(registerUserDTO);

        User savedUser = userRepository.save(any(User.class));
        assertEquals(passportEncoder.encode(savedUser), passport);
        verify(userRepository).getByEmail(registerUserDTO.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw EmailConflict error when email is already registered")
    public void shouldThrowEmailConflictError() {
        RegisterUserDTO registerUserDTO = RegisterUserDTO.builder()
            .name("John Doe")
            .email("john.doe@example.com")
            .password("password123")
            .build();

        when(userRepository.getByEmail(registerUserDTO.getEmail())).thenReturn(Optional.of(new User()));

        AuthError.EmailConflict exception = assertThrows(AuthError.EmailConflict.class, () -> {
            authUsecase.register(registerUserDTO);
        });

        assertEquals("The email john.doe@example.com is occupied!", exception.getMessage());
        verify(userRepository).getByEmail(registerUserDTO.getEmail());
    }
}
