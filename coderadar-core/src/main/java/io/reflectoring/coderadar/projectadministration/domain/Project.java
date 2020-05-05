package io.reflectoring.coderadar.projectadministration.domain;

import java.util.Date;
import java.util.List;

import io.reflectoring.coderadar.useradministration.domain.Team;
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
  private boolean vcsOnline;
  private Date vcsStart;
  private List<Team> teams;
}
