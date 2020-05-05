package io.reflectoring.coderadar.graph.useradministration.domain;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
@Data
@NoArgsConstructor
public class TeamEntity {
  private Long id;
  private String name;

  @Relationship(value = "IS_IN", direction = Relationship.INCOMING)
  private List<UserEntity> members;
}
