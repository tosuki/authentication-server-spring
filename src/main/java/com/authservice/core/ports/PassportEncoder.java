package com.authservice.core.ports;

import com.authservice.core.model.Session;
import com.authservice.core.model.User;

public interface PassportEncoder {
    /**
     * Encodes a User object into a passport string.
     * The passport string is a serialized representation of the Session object,
     * which can be used for authentication and session management.
     * @param user
     * @return
     */
    String encode(User user);

    /**
     * Decodes a passport string back into a Session object.
     * The passport string should be in the format that was produced by the encode method.
     * @param passport
     * @return
     */
    Session decode(String passport);
    boolean isExpired(Session session);
}
