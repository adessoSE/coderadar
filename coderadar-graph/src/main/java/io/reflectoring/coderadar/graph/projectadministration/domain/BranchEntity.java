package io.reflectoring.coderadar.graph.projectadministration.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/** @see io.reflectoring.coderadar.projectadministration.domain.Branch */
@Data
@NoArgsConstructor
@NodeEntity
public class BranchEntity {

  private Long id;
  @Index private String name;

  @Relationship(type = "POINTS_TO")
  private CommitEntity commit;
}
