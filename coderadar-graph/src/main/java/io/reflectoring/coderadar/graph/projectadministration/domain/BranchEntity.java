package io.reflectoring.coderadar.graph.projectadministration.domain;

import io.reflectoring.coderadar.domain.Branch;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;

/** @see Branch */
@Data
@NoArgsConstructor
@NodeEntity
public class BranchEntity {
  private Long id;
  private String name;
  private long commitHash;
  private boolean isTag;
}
