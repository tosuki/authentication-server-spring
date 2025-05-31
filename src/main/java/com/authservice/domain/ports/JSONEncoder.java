package com.authservice.domain.ports;

import com.authservice.core.io.IllegalError;
import com.authservice.core.model.Session;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONEncoder {
    String encode(Session session) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(session);
        } catch (Exception e) {
            throw new IllegalError.JSONSerializationError("json encoder", e);
        }
    }

    Session decode(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(jsonString, Session.class);
        } catch (Exception e) {
            throw new IllegalError.JSONDeserializationError("json decoder", e);
        }
    }
}
