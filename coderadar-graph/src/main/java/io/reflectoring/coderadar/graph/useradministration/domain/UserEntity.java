package io.reflectoring.coderadar.graph.useradministration.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/** a user of application, who has to login to access to functionality */
@NodeEntity
@Data
public class UserEntity {
  private Long id;
  private String username;
  private String password;

  @Relationship(value = "HAS")
  private List<RefreshTokenEntity> refreshTokens;
}
