package io.reflectoring.coderadar.graph.projectadministration.domain;

import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzerConfigurationEntity;
import io.reflectoring.coderadar.graph.useradministration.domain.TeamEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** @see io.reflectoring.coderadar.projectadministration.domain.Project */
@NodeEntity
@Data
public class ProjectEntity {

  private Long id;
  private String name;
  private String workdirName;
  private String vcsUrl;
  private String vcsUsername;
  private String vcsPassword;
  private boolean vcsOnline;
  private Date vcsStart;

  private boolean isBeingProcessed = false;
  private boolean isBeingDeleted = false;

  // The graph starts from a project and goes only in one direction.
  // https://en.wikipedia.org/wiki/Directed_acyclic_graph
  @Relationship(type = "CONTAINS")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<ModuleEntity> modules = new ArrayList<>();

  @Relationship(type = "CONTAINS")
  @ToString.Exclude
  private List<FileEntity> files = new ArrayList<>();

  @Relationship(type = "CONTAINS_COMMIT")
  @ToString.Exclude
  private List<CommitEntity> commits = new ArrayList<>();

  @Relationship(type = "HAS")
  private List<FilePatternEntity> filePatterns = new ArrayList<>();

  @Relationship(type = "HAS")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<AnalyzerConfigurationEntity> analyzerConfigurations = new ArrayList<>();

  @Relationship(type = "HAS_BRANCH")
  private List<BranchEntity> branches = new ArrayList<>();

  @Relationship(type = "HAS_TEAM")
  private List<TeamEntity> teams;
}
