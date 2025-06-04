package com.authservice.core.model;

import java.util.concurrent.Delayed;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class EmailConfirmation implements Delayed {
    private String confirmationCode;
    private User user;
    long expirationTime;

    @Override
    public long getDelay(java.util.concurrent.TimeUnit unit) {
        long delay = expirationTime - System.currentTimeMillis();
        return unit.convert(delay, java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    /**
     * Delay Queue stores values in order of which one expires first
     * This method is used to compare two Delayed objects and see who should die first!
     *  Returns -1 if a < b
        Returns 0 if a == b
        Returns 1 if a > b
     */
    @Override
    public int compareTo(Delayed o) {
        EmailConfirmation other = (EmailConfirmation) o;
        
        long thisDelay = this.getDelay(java.util.concurrent.TimeUnit.MILLISECONDS);
        long otherDelay = other.getDelay(java.util.concurrent.TimeUnit.MILLISECONDS);

        return thisDelay < otherDelay ? -1 : (thisDelay > otherDelay ? 1 : 0);
    }

    @Override
    public String toString() {
        return "EmailConfirmation{" +
                "confirmationCode='" + confirmationCode + '\'' +
                ", user=" + user +
                ", expirationTime=" + expirationTime +
                '}';
    }
}
