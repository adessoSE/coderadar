package io.reflectoring.coderadar.graph.useradministration.domain;

import java.util.List;
import lombok.Data;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/** @see io.reflectoring.coderadar.useradministration.domain.User */
@NodeEntity
@Data
public class UserEntity {
  private Long id;

  @Index(unique = true)
  private String username;

  private String password;

  @Relationship(value = "HAS")
  private List<RefreshTokenEntity> refreshTokens;
}
