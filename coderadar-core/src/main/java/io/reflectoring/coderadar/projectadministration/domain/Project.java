package io.reflectoring.coderadar.projectadministration.domain;

import java.util.Date;
import lombok.Data;

/** A Coderadar project that defines the source of files that are to be analyzed. */
@Data
public class Project {
  private long id;
  private String name;
  private String workdirName;
  private String vcsUrl;
  private String vcsUsername;
  private String vcsPassword;
  private Date vcsStart;
}
