package org.wickedsource.coderadar.security.authentication.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.security.domain.UserCredentialsResource;
import org.wickedsource.coderadar.security.service.SecretKeyService;

import javax.crypto.SecretKey;
import java.util.UUID;

@Service
public class AuthenticationService {

    private final SecretKeyService secretKeyService;

    private static final Integer TOKEN_VALIDITY_DURATION_IN_DAYS = 7;

    @Autowired
    public AuthenticationService(SecretKeyService secretKeyService) {
        this.secretKeyService = secretKeyService;
    }

    public String createToken(UserCredentialsResource userCredentialsResource) {
        DateTime dateTime = new DateTime();
        dateTime.plusDays(TOKEN_VALIDITY_DURATION_IN_DAYS);
        SecretKey secretKey = secretKeyService.getSecretKey();
        String jwtId = UUID.randomUUID().toString();
        return JWT.create().withIssuer("reflectoring.io").withSubject(userCredentialsResource.getUsername()).withExpiresAt(dateTime.toDate()).withJWTId(jwtId)
                .sign(Algorithm.HMAC256(secretKey.getEncoded()));
    }
}
