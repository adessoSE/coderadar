package io.reflectoring.coderadar.graph.useradministration.domain;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/** @see io.reflectoring.coderadar.useradministration.domain.RefreshToken */
@NodeEntity
@Data
public class RefreshTokenEntity {
  private Long id;

  @Index(unique = true)
  private String token;

  @Relationship(value = "HAS", direction = INCOMING)
  @EqualsAndHashCode.Exclude
  private UserEntity user;
}
