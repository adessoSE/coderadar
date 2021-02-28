package io.reflectoring.coderadar.graph.projectadministration.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;

/** @see io.reflectoring.coderadar.projectadministration.domain.Branch */
@Data
@NoArgsConstructor
@NodeEntity
public class BranchEntity {
  private Long id;
  private String name;
  private long commitHash;
  private boolean isTag;
}
