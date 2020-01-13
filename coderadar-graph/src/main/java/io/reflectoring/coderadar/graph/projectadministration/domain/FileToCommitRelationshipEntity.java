package io.reflectoring.coderadar.graph.projectadministration.domain;

import io.reflectoring.coderadar.plugin.api.ChangeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.springframework.data.neo4j.annotation.QueryResult;

/** @see io.reflectoring.coderadar.projectadministration.domain.FileToCommitRelationship */
@RelationshipEntity("CHANGED_IN")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@QueryResult
@Data
public class FileToCommitRelationshipEntity {
  private Long id;

  @Property private ChangeType changeType;

  @Property private String oldPath;

  @StartNode private FileEntity file;

  @EndNode private CommitEntity commit;
}
