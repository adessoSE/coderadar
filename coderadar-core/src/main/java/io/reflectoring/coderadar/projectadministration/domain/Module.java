package io.reflectoring.coderadar.projectadministration.domain;

import io.reflectoring.coderadar.analyzer.domain.File;
import java.util.LinkedList;
import java.util.List;
import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * The codebase may be organized into modules, each module starting at a certain path. All files
 * within that path are considered to be part of the module.
 */
@NodeEntity
@Data
public class Module {
  private Long id;
  private String path;

  @Relationship(direction = Relationship.INCOMING, type = "CONTAINS")
  private Project project;

  @Relationship(type = "CONTAINS")
  private List<File> files = new LinkedList<>();
}
