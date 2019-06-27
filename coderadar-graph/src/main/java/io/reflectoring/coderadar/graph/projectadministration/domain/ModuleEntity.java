package io.reflectoring.coderadar.graph.projectadministration.domain;

import io.reflectoring.coderadar.graph.analyzer.domain.FileEntity;
import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.LinkedList;
import java.util.List;

/**
 * The codebase may be organized into modules, each module starting at a certain path. All files
 * within that path are considered to be part of the module.
 */
@NodeEntity
@Data
public class ModuleEntity {
  private Long id;
  private String path;

  @Relationship(direction = Relationship.INCOMING, type = "CONTAINS")
  private ProjectEntity project;

  @Relationship(type = "CONTAINS")
  private List<FileEntity> files = new LinkedList<>();
}
