package com.authservice.domain.ports;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.MissingClaimException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.authservice.core.io.AuthError;
import com.authservice.core.io.IllegalError;
import com.authservice.core.io.IllegalError.JSONDeserializationError;
import com.authservice.core.model.Session;
import com.authservice.core.model.User;
import com.authservice.core.ports.PassportEncoder;

public class JWTPassportEncoder implements PassportEncoder {
    private final Algorithm algorithm;
    private final long expirationTime = 3600000; // 1 hour in milliseconds

    public JWTPassportEncoder(String secretKey) {
        this.algorithm = Algorithm.HMAC256(secretKey); //HMAC256 algorithm for JWT signing
    }


    @Override
    public String encode(User user) {
        Session session = Session.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .roleID(user.getRoleID())
                .expiresAt(System.currentTimeMillis() + expirationTime)
                .issuedAt(System.currentTimeMillis())
                .build();

        try {
            String passport = JWT.create()
                    .withIssuer("auth-service")
                    .withSubject(user.getId())
                    .withClaim("session", JSONSerializer.encode(session))
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
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth-service")
                    .withClaimPresence("session")
                    .build();

            DecodedJWT decodedJWT = verifier.verify(passport);
            Session session = JSONSerializer.decode(decodedJWT.getClaim("session").asString());

            String subject = decodedJWT.getSubject();

            if (subject == null || !subject.equals(session.getId())) {
                throw new AuthError.InvalidPassport("jwt passport encoder - decode: subject mismatch");
            }

            return session;
        } catch (MissingClaimException e) {
            throw new AuthError.InvalidPassport("jwt passport encoder - decode: missing claim");
        } catch (TokenExpiredException e) {
            throw new AuthError.ExpiredToken("jwt passport encoder - decode: token expired");
        } catch (AlgorithmMismatchException e) {
            throw new AuthError.InvalidPassport("jwt passport encoder - decode: algorithm mismatch");
        } catch (JWTDecodeException e) {
            throw new AuthError.InvalidPassport("jwt passport encoder - decode: JWT decode error");
        } catch (JSONDeserializationError e) {
            throw new AuthError.InvalidPassport("jwt passport encoder" + " - decode");
        } catch (Exception e) {
            throw new IllegalError.UnknownError(e, "jwt passport encoder - decode");
        }
    }

    @Override
    public boolean isExpired(Session session) {
        return session.getExpiresAt() < System.currentTimeMillis();
    }
}
