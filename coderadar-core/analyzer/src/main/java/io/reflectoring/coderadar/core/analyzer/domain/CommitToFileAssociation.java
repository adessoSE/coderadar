package io.reflectoring.coderadar.core.analyzer.domain;

import io.reflectoring.coderadar.plugin.api.ChangeType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * Associates a Commit to a File. Each Commit is associated to all Files that have been modified in
 * the Commit and to all files that have been left untouched by it, so that one can easily access
 * the full set of files at the time of the Commit.
 */
@RelationshipEntity("TOUCHES")
@NoArgsConstructor
@Data
public class CommitToFileAssociation {
  private Long id;
  private ChangeType changeType;

  @StartNode
  private Commit commit;

  @EndNode
  private File file;
}
