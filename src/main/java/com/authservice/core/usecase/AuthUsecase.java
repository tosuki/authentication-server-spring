package com.authservice.core.usecase;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.authservice.core.dto.AuthenticateUserDTO;
import com.authservice.core.dto.RegisterUserDTO;
import com.authservice.core.io.AuthError;
import com.authservice.core.io.IllegalError;
import com.authservice.core.model.EmailConfirmation;
import com.authservice.core.model.PassportExpiration;
import com.authservice.core.model.PassportValidation;
import com.authservice.core.model.Session;
import com.authservice.core.model.User;
import com.authservice.core.model.UserRole;
import com.authservice.core.ports.EmailServiceProvider;
import com.authservice.core.ports.PassportEncoder;
import com.authservice.core.ports.PasswordEncoder;
import com.authservice.core.queue.EmailConfirmationQueue;
import com.authservice.core.repository.UserRepository;

@Service
public class AuthUsecase {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private PassportEncoder passportEncoder;
    private EmailConfirmationQueue emailConfirmationQueue;
    private EmailServiceProvider emailServiceProvider;

    public AuthUsecase(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        PassportEncoder passportEncoder,
        EmailConfirmationQueue emailConfirmationQueue,
        EmailServiceProvider emailServiceProvider
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passportEncoder = passportEncoder;
        this.emailServiceProvider = emailServiceProvider;
        this.emailConfirmationQueue = emailConfirmationQueue;
    }

    public PassportValidation validatePassport(String passport) {
        try {
            Session session = passportEncoder.decode(passport);

            return PassportValidation.builder()
                .passport(passport)
                .session(session)
                .expiration(PassportExpiration.VALID)
                .build();
        } catch (AuthError.ExpiredToken expiredToken) {
            return PassportValidation.builder()
                .passport(passport)
                .session(null)
                .expiration(PassportExpiration.EXPIRED)
                .build();

        } catch (AuthError.InvalidPassport invalidPassport) {
            return PassportValidation.builder()
                .passport(passport)
                .session(null)
                .expiration(PassportExpiration.INVALID)
                .build();
        } catch (Exception exception) {
            throw new IllegalError.UnknownError(exception, "auth usecase - validatePassport");
        }
    }

    public EmailConfirmation register(RegisterUserDTO data) {
        try {
            Optional<User> emailConflicts = userRepository.getByEmail(data.email);

            if (!emailConflicts.isEmpty()) {
                throw new AuthError.EmailConflict("auth usecase - register|isEmpty", data.email);
            }

            String encryptedPassword = passwordEncoder.encode(data.password);            
            User user = User.builder()
                .id(UUID.randomUUID().toString())
                .name(data.name)
                .email(data.email)
                .password(encryptedPassword)
                .roleID(UserRole.NORMAL.getRoleID())
                .verified(false)
                .build();

            EmailConfirmation emailConfirmation = EmailConfirmation.builder()
                .confirmationCode(UUID.randomUUID().toString())
                .user(user)
                .expirationTime(System.currentTimeMillis() + 60*5*1000)//5 minutes
                .build();

            emailServiceProvider.sendEmail(
                emailConfirmation.getConfirmationCode(),
                user.getEmail()
            );
            emailConfirmationQueue.add(emailConfirmation);

            return emailConfirmation;
        } catch (AuthError authError) {
            throw authError;
        } catch (Exception exception) {
            throw new IllegalError.UnknownError(exception, "auth usecase - register");
        }
    }

    public String confirmEmail(String confirmationCode) {
        try {
            Optional<EmailConfirmation> emailConfirmationOptional = emailConfirmationQueue.getByConfirmationCode(confirmationCode);
            
            if (emailConfirmationOptional.isEmpty()) {
                throw new AuthError.InvalidConfirmationCode("auth usecase - confirmEmail|isEmpty");
            }

            EmailConfirmation emailConfirmation = emailConfirmationOptional.get();
            User user = userRepository.save(emailConfirmation.getUser());

            String passport = passportEncoder.encode(user);

            return passport;
        } catch (AuthError authError) {
            throw authError;
        } catch (Exception exception) {
            throw new IllegalError.UnknownError(exception, "auth usecase - confirmEmail");
        }
    }

    public String authenticate(AuthenticateUserDTO data) {
        try {
            Optional<User> user = userRepository.getByEmail(data.email);

            if (user.isEmpty()) {
                throw new AuthError.WrongCredentials("auth usecase - authenticate|isEmpty");
            }

            if (!passwordEncoder.match(user.get().getPassword(), data.password)) {
                throw new AuthError.WrongCredentials("auth usecase - authenticate|matches");
            }

            return passportEncoder.encode(user.get());
        } catch (AuthError authError) {
            throw authError;
        } catch (Exception exception) {
            throw new IllegalError.UnknownError(exception, "auth usecase - authenticate");
        }
    }
}
