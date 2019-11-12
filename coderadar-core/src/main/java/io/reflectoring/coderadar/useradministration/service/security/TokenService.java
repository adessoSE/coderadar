package io.reflectoring.coderadar.useradministration.service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import java.util.Date;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Service for generation and verification of authentication tokens. */
@Service
public class TokenService {

  private final CoderadarConfigurationProperties configuration;

  private final SecretKeyService secretKeyService;

  @Autowired
  public TokenService(
      CoderadarConfigurationProperties configuration, SecretKeyService secretKeyService) {
    this.configuration = configuration;
    this.secretKeyService = secretKeyService;
  }

  /**
   * This method generates a JSON Web Token for access to resources. The token contains the
   * expiration date, userId, username and issuer and is signed with HMAC256.
   *
   * @param userId id of the user, that acquires the token
   * @param username username of the user, that acquires the token
   * @return JSON Web Token
   */
  public String generateAccessToken(Long userId, String username) {
    Date expiresAt =
        DateTime.now()
            .plusMinutes(configuration.getAuthentication().getAccessTokenDurationInMinutes())
            .toDate();
    TokenType tokenType = TokenType.ACCESS;
    return generateToken(userId, username, expiresAt, tokenType);
  }

  String generateToken(Long userId, String username, Date expiresAt, TokenType tokenType) {
    byte[] secret = secretKeyService.getSecretKey().getEncoded();
    return JWT.create() //
        .withExpiresAt(expiresAt) //
        .withIssuedAt(new Date()) //
        .withIssuer("coderadar") //
        .withClaim("userId", userId.toString()) //
        .withClaim("username", username) //
        .withClaim("type", tokenType.toString())
        .sign(Algorithm.HMAC256(secret));
  }

  /**
   * Verifies the JSON Web Token with the secret key.
   *
   * @param token JSON Web Token to be verified
   * @return decoded Token
   */
  public DecodedJWT verify(String token) {
    byte[] secret = secretKeyService.getSecretKey().getEncoded();
    JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).withIssuer("coderadar").build();
    try {
      return verifier.verify(token);
    } catch (SignatureVerificationException e) {
      return null;
    }
  }

  /**
   * Returns
   *
   * <ul>
   *   <li><code>true</code> if the token signature is valid but the token is expired
   *   <li><code>false</code> if the token signature is valid and the token is not expired
   *   <li>throws {@link JWTVerificationException} if the token signature is not valid.
   * </ul>
   *
   * @param token access token to be checked.
   */
  public boolean isExpired(String token) {
    byte[] secret = secretKeyService.getSecretKey().getEncoded();
    // specify a leeway window in which the token is still considered valid
    int leeway = configuration.getAuthentication().getRefreshTokenDurationInMinutes() * 60;
    JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).acceptExpiresAt(leeway).build();
    // verify signature and claims
    try {
      verifier.verify(token);
    } catch (JWTVerificationException e) {
      return false;
    }
    JWT jwtToken = JWT.decode(token);
    return new Date().after(jwtToken.getExpiresAt());
  }

  /**
   * This method generates a JSON Web Token for refreshing a access token. The refresh token
   * contains the expiration date, userId, username and issuer and is signed with HMAC256.
   *
   * @param userId id of the user, that acquires the token
   * @param username username of the user, that acquires the token
   * @return JSON Web Token
   */
  public String generateRefreshToken(Long userId, String username) {
    Date expiresAt =
        DateTime.now()
            .plusMinutes(configuration.getAuthentication().getRefreshTokenDurationInMinutes())
            .toDate();
    TokenType tokenType = TokenType.REFRESH;
    return generateToken(userId, username, expiresAt, tokenType);
  }

  /**
   * Returns username from the tokens claim <code>username</code>.
   *
   * @param refreshToken a jwt token
   */
  public String getUsername(String refreshToken) {
    JWT jwt = JWT.decode(refreshToken);
    Claim claim = jwt.getClaim("username");
    return claim.asString();
  }
}
