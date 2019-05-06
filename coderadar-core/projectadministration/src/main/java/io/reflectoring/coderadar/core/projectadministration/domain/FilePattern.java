package io.reflectoring.coderadar.core.projectadministration.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Contains an Ant-style path pattern to define a certain set of files that is of importance to a
 * coderadar Project.
 */
@NodeEntity
@Data
public class FilePattern {
  private Long id;
  private String pattern;
  private InclusionType inclusionType; // TODO:  A converter may have to be used here.
  private FileSetType fileSetType; // TODO:  A converter may have to be used here.
  private Project project;
}
