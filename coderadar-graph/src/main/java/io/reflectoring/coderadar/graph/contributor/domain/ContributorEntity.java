package io.reflectoring.coderadar.graph.contributor.domain;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
@Data
public class ContributorEntity {
  private Long id;
  private String displayName;
  private Set<String> names;
  private Set<String> emails;

  @Relationship(type = "WORKS_ON")
  private List<ProjectEntity> projects = new ArrayList<>();
}
