package org.wickedsource.coderadar.security.authentication.rest;

import java.util.UUID;

import javax.crypto.SecretKey;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class AuthenticationService {

    private final SecretKeyService secretKeyService;

    private static final Integer TOKEN_VALIDITY_DURATION_IN_DAYS = 7;

    @Autowired
    public AuthenticationService(SecretKeyService secretKeyService) {
        this.secretKeyService = secretKeyService;
    }

    public String createToken(UserLoginResource userLoginResource) {
        DateTime dateTime = new DateTime();
        dateTime.plusDays(TOKEN_VALIDITY_DURATION_IN_DAYS);
        SecretKey secretKey = secretKeyService.getSecretKey();
        String jwtId = UUID.randomUUID().toString();
        return JWT.create().withIssuer("reflectoring.io").withSubject(userLoginResource.getUsername()).withExpiresAt(dateTime.toDate()).withJWTId(jwtId)
                .sign(Algorithm.HMAC256(secretKey.getEncoded()));
    }
}
