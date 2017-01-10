package org.wickedsource.coderadar.security.service;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.core.configuration.CoderadarConfiguration;
import org.wickedsource.coderadar.security.TokenType;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

/**
 * Service for generation and verification of authentication tokens.
 */
@Service
public class TokenService {

    private final CoderadarConfiguration configuration;

    private final SecretKeyService secretKeyService;

    @Autowired
    public TokenService(CoderadarConfiguration configuration, SecretKeyService secretKeyService) {
        this.configuration = configuration;
        this.secretKeyService = secretKeyService;
    }

    /**
     * This method generates a JSON Web Token for access to resources. The token contains the expiration date, userId, username and issuer and is signed with
     * HMAC256.
     * 
     * @param userId
     *            id of the user, that acquires the token
     * @param username
     *            username of the user, that acquires the token
     * @return JSON Web Token
     */
    public String generateAccessToken(Long userId, String username) {
        byte[] secret = secretKeyService.getSecretKey().getEncoded();
        Date expiresAt = DateTime.now().plusMinutes(configuration.getAccessTokenDuration()).toDate();
        TokenType tokenType = TokenType.ACCESS;
        return generateToken(userId, username, expiresAt, tokenType);
    }

    String generateToken(Long userId, String username, Date expiresAt, TokenType tokenType) {
        byte[] secret = secretKeyService.getSecretKey().getEncoded();
        return JWT.create()//
                .withExpiresAt(expiresAt)//
                .withIssuedAt(new Date())
                .withIssuer("coderadar")//
                .withClaim("userId", userId.toString())//
                .withClaim("username", username)//
                .withClaim("type", tokenType.toString()).sign(Algorithm.HMAC256(secret));
    }

    /**
     * Verifies the JSON Web Token with the secret key.
     * 
     * @param token
     *            JSON Web Token to be verified
     */
    public void verify(String token) {
        byte[] secret = secretKeyService.getSecretKey().getEncoded();
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).withIssuer("coderadar").build();
        verifier.verify(token);
    }

    /**
     * This method generates a JSON Web Token for refreshing a access token. The refresh token contains the expiration date, userId, username and issuer and is
     * signed with HMAC256.
     *
     * @param userId
     *            id of the user, that acquires the token
     * @param username
     *            username of the user, that acquires the token
     * @return JSON Web Token
     */
    public String generateRefreshToken(Long userId, String username) {
        Date expiresAt = DateTime.now().plusMinutes(configuration.getRefreshTokenDuration()).toDate();
        TokenType tokenType = TokenType.REFRESH;
        return generateToken(userId, username, expiresAt, tokenType);
    }
}
