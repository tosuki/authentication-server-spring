package com.authservice.core.queue;

import java.util.Optional;
import java.util.concurrent.DelayQueue;

import com.authservice.core.model.EmailConfirmation;

public interface EmailConfirmationQueue {
    public void add(EmailConfirmation emailConfirmation);
    public Optional<EmailConfirmation> getByConfirmationCode(String confirmationCode);
    public void startWatcher();
    public void interruptWatcher();
}
