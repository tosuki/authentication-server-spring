package com.authservice.domain.ports;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.authservice.core.io.AuthError;
import com.authservice.core.model.Session;
import com.authservice.core.model.User;
import com.authservice.core.ports.PassportEncoder;

public class JWTPassportEncoder implements PassportEncoder {
    private final String secretKey;
    private final Algorithm algorithm;
    private final long expirationTime = 3600000; // 1 hour in milliseconds

    public JWTPassportEncoder(String secretKey) {
        this.secretKey = secretKey;
        this.algorithm = Algorithm.HMAC256(secretKey); //HMAC256 algorithm for JWT signing
    }


    @Override
    public String encode(User user) {
        Session session = Session.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .expiresAt(System.currentTimeMillis() + expirationTime)
                .issuedAt(System.currentTimeMillis())
                .build();

        try {
            String passport = JWT.create()
                    .withIssuer("auth-service")
                    .withSubject(user.getId())
                    .withPayload(JSONSerializer.encode(session)) // Assuming JSONSerializer is a utility to convert Session to JSON
                    .withIssuedAt(new java.util.Date(session.getIssuedAt()))
                    .withExpiresAt(new java.util.Date(session.getExpiresAt()))
                    .sign(algorithm);

            return passport;
        } catch (Exception e) {
            throw new AuthError.InvalidPassport("jwt passport encoder - encode");
        }
    }

    @Override
    public Session decode(String passport) {
        throw new UnsupportedOperationException("Unimplemented method 'decode'");
    }

    @Override
    public boolean isExpired(Session session) {
        throw new UnsupportedOperationException("Unimplemented method 'isExpired'");
    }
    
}
