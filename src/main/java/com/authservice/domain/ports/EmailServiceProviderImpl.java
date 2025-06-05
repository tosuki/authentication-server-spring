package com.authservice.domain.ports;

import com.authservice.core.io.Logger;
import com.authservice.core.ports.EmailServiceProvider;

public class EmailServiceProviderImpl implements EmailServiceProvider {
    private boolean shouldMock = true;

    public EmailServiceProviderImpl(boolean shouldMock) {
        this.shouldMock = shouldMock;
    }

    @Override
    public void sendEmail(String message, String recipient) {
        if (shouldMock) {
            Logger.info("Mock email sent to " + recipient + " with message: " + message);
            return;
        }
    }
}
