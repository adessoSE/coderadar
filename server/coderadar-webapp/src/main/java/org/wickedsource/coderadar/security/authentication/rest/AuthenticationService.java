package org.wickedsource.coderadar.security.authentication.rest;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class AuthenticationService {

    private static final Integer TOKEN_VALIDITY_DURATION_IN_DAYS = 7;

    public String createToken(UserLoginResource userLoginResource) {
        DateTime dateTime = new DateTime();
        dateTime.plusDays(TOKEN_VALIDITY_DURATION_IN_DAYS);
        byte[] secretKey = getSecretKey();
        String jwtId = UUID.randomUUID().toString();
        return JWT.create().withIssuer(userLoginResource.getUsername()).withExpiresAt(dateTime.toDate()).withJWTId(jwtId).sign(Algorithm.HMAC256(secretKey));
    }

    private byte[] getSecretKey() {
        // TODO get secret bytes from key provider
        return new byte[32];
    }
}
