package io.reflectoring.coderadar.domain;

import lombok.Data;

/**
 * Refresh token is a JSON Web Token that is used by a client to get a new access token. In contrast
 * to access token the refresh token is persisted to have the possibility to assign the token to the
 * user entity and to revoke it.
 */
@Data
public class RefreshToken {
  private long id;
  private String token;
}
