package com.authservice.core.repository;

import java.util.HashMap;
import java.util.Optional;

import com.authservice.core.model.EmailConfirmation;

public class EmailConfirmationRepositoryImpl implements EmailConfirmationRepository{
    private final HashMap<String, EmailConfirmation> emailConfirmations = new HashMap<>();
    
    @Override
    public EmailConfirmation save(EmailConfirmation emailConfirmation) {
        emailConfirmations.put(emailConfirmation.getConfirmationCode(), emailConfirmation);

        return emailConfirmation;
    }

    @Override
    public Optional<EmailConfirmation> getByUserId(String userId) {
        return emailConfirmations.values().stream()
            .filter(emailConfirmation -> emailConfirmation.getUserId().equals(userId))
            .findFirst();   
    }

    @Override
    public Optional<EmailConfirmation> updateStatus(boolean isConfirmed, String userId) {
        return emailConfirmations.values().stream()
            .filter(emailConfirmation -> emailConfirmation.getUserId().equals(userId))
            .findFirst()
            .map(emailConfirmation -> {
                emailConfirmation.setConfirmed(isConfirmed);
                return emailConfirmation;
            });
    }

    @Override
    public Optional<EmailConfirmation> getByConfirmationCode(String confirmationCode) {
        return Optional.ofNullable(emailConfirmations.get(confirmationCode));
    }

    @Override
    public void deleteByUserId(String userId) {
        emailConfirmations.values().removeIf(emailConfirmation -> emailConfirmation.getUserId().equals(userId));
    }

    @Override
    public void deleteByConfirmationCode(String confirmationCode) {
        emailConfirmations.remove(confirmationCode);
    }

    @Override
    public void deleteAll() {
        emailConfirmations.clear();
    }
}
