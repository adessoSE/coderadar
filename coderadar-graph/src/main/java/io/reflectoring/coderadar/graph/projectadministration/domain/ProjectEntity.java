package io.reflectoring.coderadar.graph.projectadministration.domain;

import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzingJobEntity;
import io.reflectoring.coderadar.graph.analyzer.domain.CommitEntity;
import io.reflectoring.coderadar.graph.analyzer.domain.FileEntity;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/** A coderadar project that defines the source of files that are to be analyzed. */
@NodeEntity
@Data
@EqualsAndHashCode(
  exclude = {"files", "filePatterns", "modules", "analyzerConfigurations", "analyzingJob"}
)
@ToString(exclude = {"files", "filePatterns", "modules", "analyzerConfigurations", "analyzingJob"})
public class ProjectEntity {
  private Long id;
  private String name;
  private String workdirName;
  private String vcsUrl;
  private String vcsUsername;
  private String vcsPassword;
  private boolean vcsOnline;
  private Date vcsStart;
  private Date vcsEnd;

  private boolean isBeingProcessed;

  // The graph starts from a project and goes only in one direction.
  // https://en.wikipedia.org/wiki/Directed_acyclic_graph
  @Relationship(type = "CONTAINS")
  private List<ModuleEntity> modules = new LinkedList<>();

  @Relationship(type = "CONTAINS")
  private List<FileEntity> files = new LinkedList<>();

  @Relationship(type = "CONTAINS")
  private List<CommitEntity> commits = new LinkedList<>();

  @Relationship(type = "HAS")
  private List<FilePatternEntity> filePatterns = new LinkedList<>();

  @Relationship(type = "HAS")
  private List<AnalyzerConfigurationEntity> analyzerConfigurations = new LinkedList<>();

  @Relationship(type = "HAS")
  private AnalyzingJobEntity analyzingJob;
}
