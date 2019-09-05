package io.reflectoring.coderadar.graph.projectadministration.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * Refresh token is a JSON Web Token that is used by a client to get a new access token. In contrast
 * to access token the refresh token is persisted to have the possibility to assign the token to the
 * user entity and to revoke it.
 */
@NodeEntity
@Data
public class RefreshTokenEntity {
  private Long id;
  private String token;

  @Relationship private UserEntity user;
}
