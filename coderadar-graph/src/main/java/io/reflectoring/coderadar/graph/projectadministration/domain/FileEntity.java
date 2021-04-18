package io.reflectoring.coderadar.graph.projectadministration.domain;

import io.reflectoring.coderadar.domain.File;
import java.util.Collections;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/** @see File */
@NodeEntity
@Data
@EqualsAndHashCode
@NoArgsConstructor
public class FileEntity {
  private Long id;
  private String path;

  @EqualsAndHashCode.Exclude
  @Relationship(type = "RENAMED_FROM")
  @ToString.Exclude
  private List<FileEntity> oldFiles = Collections.emptyList();

  public FileEntity(String path) {
    this.path = path;
  }
}
