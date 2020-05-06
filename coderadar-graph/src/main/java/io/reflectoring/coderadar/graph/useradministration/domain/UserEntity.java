package io.reflectoring.coderadar.graph.useradministration.domain;

import java.util.List;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/** @see io.reflectoring.coderadar.useradministration.domain.User */
@NodeEntity
@Data
public class UserEntity {
  private Long id;
  private String username;
  private String password;

  @Relationship(value = "HAS")
  private List<RefreshTokenEntity> refreshTokens;

  @Relationship(type = "ASSIGNED_TO")
  private List<ProjectEntity> projects;
}
