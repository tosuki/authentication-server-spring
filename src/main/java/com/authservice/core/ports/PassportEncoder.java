package com.authservice.core.ports;

import com.authservice.core.model.Session;
import com.authservice.core.model.User;

public interface PassportEncoder {
    String encode(User user);
    Session decode(String passport);
}
