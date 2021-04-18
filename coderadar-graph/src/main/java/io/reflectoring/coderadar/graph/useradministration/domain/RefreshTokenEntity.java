package io.reflectoring.coderadar.graph.useradministration.domain;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

import io.reflectoring.coderadar.domain.RefreshToken;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/** @see RefreshToken */
@NodeEntity
@Data
public class RefreshTokenEntity {
  private Long id;
  private String token;

  @Relationship(value = "HAS", direction = INCOMING)
  @EqualsAndHashCode.Exclude
  private UserEntity user;
}
