package com.authservice.core.repository;

import java.util.Optional;

import com.authservice.core.model.EmailConfirmation;

public interface EmailConfirmationRepository {
    void save(EmailConfirmation emailConfirmation);
    Optional<EmailConfirmation> getByUserId(String userId);
    Optional<EmailConfirmation> updateStatus(boolean isConfirmed, String userId);
}
