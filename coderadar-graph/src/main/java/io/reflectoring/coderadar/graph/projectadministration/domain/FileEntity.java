package io.reflectoring.coderadar.graph.projectadministration.domain;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/** @see io.reflectoring.coderadar.projectadministration.domain.File */
@NodeEntity
@Data
@EqualsAndHashCode
public class FileEntity {
  private Long id;
  private int sequenceId;
  private String path;

  @EqualsAndHashCode.Exclude
  @Relationship(type = "RENAMED_FROM")
  @ToString.Exclude
  private List<FileEntity> oldFiles;
}
