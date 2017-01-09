package org.wickedsource.coderadar.security.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import javax.crypto.SecretKey;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wickedsource.coderadar.core.configuration.CoderadarConfiguration;
import org.wickedsource.coderadar.security.TokenType;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;

@RunWith(SpringJUnit4ClassRunner.class)
public class TokenServiceTest {

    public static final byte[] KEY = "symmetric key to sign and verify".getBytes();
    private TokenService tokenService;

    @Before
    public void setUp() {
        SecretKeyService secretKeyService = mock(SecretKeyService.class);
        SecretKey secretKey = mock(SecretKey.class);
        when(secretKey.getEncoded()).thenReturn(KEY);

        when(secretKeyService.getSecretKey()).thenReturn(secretKey);
        tokenService = new TokenService(mock(CoderadarConfiguration.class), secretKeyService);
    }

    @Test
    public void generateAndVerifyToken() throws Exception {
        Date expireDate = DateTime.now().plusMinutes(1).toDate();
        String token = tokenService.generateToken(1L, "user", expireDate, TokenType.ACCESS);
        JWT.require(Algorithm.HMAC256(KEY)).build().verify(token);
    }

    @Test(expected = InvalidClaimException.class)
    public void verifyTokenFails() throws Exception {
        Date expireDate = DateTime.now().minusMinutes(1).toDate();
        String token = tokenService.generateToken(1L, "user", expireDate, TokenType.ACCESS);
        JWT.require(Algorithm.HMAC256(KEY)).build().verify(token);
    }

    @Test(expected = SignatureVerificationException.class)
    public void verifyTokenSignatureFails() throws Exception {
        Date expireDate = DateTime.now().minusMinutes(1).toDate();
        String token = tokenService.generateToken(1L, "user", expireDate, TokenType.ACCESS);
        token = token.concat("add string");
        JWT.require(Algorithm.HMAC256(KEY)).build().verify(token);
    }

}
