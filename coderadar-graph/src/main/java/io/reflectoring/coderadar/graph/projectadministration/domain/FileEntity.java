package io.reflectoring.coderadar.graph.projectadministration.domain;

import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
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
  private String path;

  @EqualsAndHashCode.Exclude
  @Relationship(type = "RENAMED_FROM")
  @ToString.Exclude
  private List<FileEntity> oldFiles;

  // TODO: this is just experimental for now
  @Relationship(type = "MODIFIED_BY")
  private List<ContributorEntity> contributors;
}
