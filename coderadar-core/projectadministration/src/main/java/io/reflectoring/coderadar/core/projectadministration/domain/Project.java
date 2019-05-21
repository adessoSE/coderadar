package io.reflectoring.coderadar.core.projectadministration.domain;

import io.reflectoring.coderadar.core.analyzer.domain.Commit;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/** A coderadar project that defines the source of files that are to be analyzed. */
@NodeEntity
@Data
public class Project {
  private Long id;
  private String name;
  private String workdirName;
  private String vcsUrl;
  private String vcsUsername;
  private String vcsPassword;
  private boolean vcsOnline;
  private Date vcsStart;
  private Date vcsEnd;

  // The graph starts from a project and goes only in one direction.
  // https://en.wikipedia.org/wiki/Directed_acyclic_graph
  @Relationship(type = "HAS")
  private List<Module> modules = new LinkedList<>();

  @Relationship(type = "HAS")
  private List<FilePattern> filePatterns;

  @Relationship(type = "HAS")
  private List<AnalyzerConfiguration> analyzerConfigurations;

  @Relationship(type = "HAS")
  private AnalyzingJob analyzingJob;

  @Relationship(type = "HAS")
  private List<Commit> commits;
}
