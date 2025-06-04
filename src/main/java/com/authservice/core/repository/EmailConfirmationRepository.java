package com.authservice.core.repository;

import java.util.Optional;

import com.authservice.core.model.EmailConfirmation;

public interface EmailConfirmationRepository {
    EmailConfirmation save(EmailConfirmation emailConfirmation);
    Optional<EmailConfirmation> getByUserId(String userId);
    Optional<EmailConfirmation> updateStatus(boolean isConfirmed, String userId);
    Optional<EmailConfirmation> getByConfirmationCode(String confirmationCode);
    void deleteByUserId(String userId);
    void deleteByConfirmationCode(String confirmationCode);
    void deleteAll();
}
