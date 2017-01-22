package org.wickedsource.coderadar.security.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import java.util.Date;
import javax.crypto.SecretKey;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wickedsource.coderadar.core.configuration.CoderadarConfiguration;
import org.wickedsource.coderadar.security.TokenType;

@RunWith(SpringJUnit4ClassRunner.class)
public class TokenServiceTest {

  public static final byte[] KEY = "symmetric key to sign and verify".getBytes();

  @InjectMocks private TokenService tokenService;

  @Mock private CoderadarConfiguration configuration;

  @Mock private SecretKeyService secretKeyService;

  @Before
  public void setUp() {
    SecretKey secretKey = mock(SecretKey.class);
    when(secretKey.getEncoded()).thenReturn(KEY);
    when(secretKeyService.getSecretKey()).thenReturn(secretKey);
    when(configuration.getRefreshTokenDuration()).thenReturn(86400);
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

  @Test
  public void tokenExpiredAndSignatureValid() throws Exception {
    Date expireDate = DateTime.now().minusMinutes(14).toDate();
    String token = tokenService.generateToken(1L, "user", expireDate, TokenType.ACCESS);
    boolean result = tokenService.isExpired(token);
    assertThat(result).isTrue();
  }

  @Test
  public void tokenNotExpiredAndSignatureValid() throws Exception {
    Date expireDate = DateTime.now().plusMinutes(5).toDate();
    String token = tokenService.generateToken(1L, "user", expireDate, TokenType.ACCESS);
    boolean result = tokenService.isExpired(token);
    assertThat(result).isFalse();
  }

  @Test(expected = SignatureVerificationException.class)
  public void tokenNotExpiredAndSignatureNotValid() throws Exception {
    Date expireDate = DateTime.now().plusMinutes(5).toDate();
    String token = tokenService.generateToken(1L, "user", expireDate, TokenType.ACCESS);
    token = token.concat("invalid");
    tokenService.isExpired(token);
  }

  @Test
  public void getUsername() throws Exception {
    Date expireDate = DateTime.now().plusMinutes(1).toDate();
    String token = tokenService.generateToken(1L, "user", expireDate, TokenType.ACCESS);
    String username = tokenService.getUsername(token);
    assertThat(username).isEqualTo("user");
  }
}
