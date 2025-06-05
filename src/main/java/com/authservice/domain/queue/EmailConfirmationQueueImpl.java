package com.authservice.domain.queue;


import java.util.Optional;
import java.util.concurrent.DelayQueue;

import com.authservice.core.io.Logger;
import com.authservice.core.model.EmailConfirmation;
import com.authservice.core.queue.EmailConfirmationQueue;

public class EmailConfirmationQueueImpl implements EmailConfirmationQueue {
    private Thread cleanupThread;
    private DelayQueue<EmailConfirmation> queue = new DelayQueue<>();

    @Override
    public void add(EmailConfirmation emailConfirmation) {
        queue.add(emailConfirmation);
    }

    @Override
    public Optional<EmailConfirmation> getByConfirmationCode(String confirmationCode) {
        return queue.stream()
                .filter(emailConfirmation -> emailConfirmation.getConfirmationCode().equals(confirmationCode))
                .findFirst();
    }

    @Override
    public void startWatcher() {
        cleanupThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        EmailConfirmation emailConfirmation = queue.take();
                        Logger.warn("Processing expired email confirmation: %s", emailConfirmation);
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    Logger.warn("Stopping cleanup thread (Email Confirmation Queue)...");
                }
            }
        });

        cleanupThread.setDaemon(true);
        cleanupThread.start();
    }

    @Override
    public void interruptWatcher() {
        if (cleanupThread != null && cleanupThread.isAlive()) {
            cleanupThread.interrupt();
        }
    }
}

