package io.reflectoring.coderadar.projectadministration.domain;

import java.util.Date;
import lombok.Data;

/** A coderadar project that defines the source of files that are to be analyzed. */
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
}
