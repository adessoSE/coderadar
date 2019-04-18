package io.reflectoring.coderadar.core.projectadministration.domain;

import javax.persistence.*;
import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;

import java.net.URL;
import java.util.Date;

/** A coderadar project that defines the source of files that are to be analyzed. */
@NodeEntity
@Data
public class Project {
  private Long id;
  private String name;
  private String workdirName;

  // Information about the VCS
  private URL vcsUrl;
  private String vcsUsername;
  private String vcsPassword;
  private boolean vcsOnline;
  private Date vcsStart;
  private Date vcsEnd;
}
