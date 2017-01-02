package org.wickedsource.coderadar.security.authentication.rest;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class AuthenticationService {

    private static final Integer SESSION_DURATION_IN_MINUTES = 60;

    public String createToken(UserLoginResource userLoginResource) {
        DateTime dateTime = new DateTime();
        dateTime.plusMinutes(SESSION_DURATION_IN_MINUTES);
        byte[] secretKey = generateSecretKey();
        String jwtId = putSecretKey(secretKey);
        return JWT.create().withIssuer(userLoginResource.getUsername()).withExpiresAt(dateTime.toDate()).withJWTId(jwtId).sign(Algorithm.HMAC256(secretKey));
    }

    private String putSecretKey(byte[] secretKey) {
        // TODO save secret key for verification in a distributed cache or in the database with jwtId as key and secretKey as values
        return UUID.randomUUID().toString();
    }

    private byte[] generateSecretKey() {
        // TODO generate secret bytes
        return new byte[32];
    }
}
