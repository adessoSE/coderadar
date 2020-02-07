package io.reflectoring.coderadar.graph.projectadministration.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@Data
@NoArgsConstructor
@NodeEntity
public class BranchEntity {
  private Long id;
  private String name;

  @Relationship(type = "POINTS_TO")
  private CommitEntity commit;
}
