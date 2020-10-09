package io.reflectoring.coderadar.graph.projectadministration.domain;

import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;

/** @see io.reflectoring.coderadar.projectadministration.domain.FilePattern */
@NodeEntity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilePatternEntity {
  private Long id;
  private String pattern;
  private InclusionType inclusionType;
}
