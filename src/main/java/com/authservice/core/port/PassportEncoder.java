package com.authservice.core.port;

import com.authservice.core.model.Session;
import com.authservice.core.model.User;

public interface PassportEncoder {
    public String encode(User user);
    public Session decode(Session session);
}
