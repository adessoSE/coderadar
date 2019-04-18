package io.reflectoring.coderadar.core.projectadministration.domain;

import javax.persistence.*;
import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.net.URL;
import java.util.Date;
import java.util.List;

/** A coderadar project that defines the source of files that are to be analyzed. */
@NodeEntity
@Data
public class Project {
  private Long id;
  private String name;
  private String workdirName;
  private URL vcsUrl;
  private String vcsUsername;
  private String vcsPassword;
  private boolean vcsOnline;
  private Date vcsStart;
  private Date vcsEnd;

  // The graph starts from a project and goes only in one direction.
  // https://en.wikipedia.org/wiki/Directed_acyclic_graph
  @Relationship("HAS")
  private List<Module> modules;
}
