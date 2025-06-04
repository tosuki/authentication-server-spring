package com.authservice.core.repository;

import java.util.Optional;
import java.util.concurrent.DelayQueue;

import com.authservice.core.model.EmailConfirmation;

public class EmailConfirmationQueue {
    private Thread cleanupThread;
    private DelayQueue<EmailConfirmation> queue = new DelayQueue<>();

    public void add(EmailConfirmation emailConfirmation) {
        queue.add(emailConfirmation);
    }

    public Optional<EmailConfirmation> getByConfirmationCode(String confirmationCode) {
        return queue.stream()
                .filter(emailConfirmation -> emailConfirmation.getConfirmationCode().equals(confirmationCode))
                .findFirst();
    }

    public void startCleanupThread() {
        cleanupThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        EmailConfirmation emailConfirmation = queue.take();
                        System.out.println("Processing expired email confirmation: " + emailConfirmation);
                    }

                } catch (InterruptedException e) {
                    System.out.println("Cleanup thread interrupted, stopping...");
                    Thread.currentThread().interrupt();
                } finally {
                    System.out.println("Cleanup thread interrupted, stopping...");
                }
            }
        });

        cleanupThread.setDaemon(true);
        cleanupThread.start();
    }

    public void stopCleanupThread() {
        if (cleanupThread != null && cleanupThread.isAlive()) {
            cleanupThread.interrupt();
        }
    }
}
