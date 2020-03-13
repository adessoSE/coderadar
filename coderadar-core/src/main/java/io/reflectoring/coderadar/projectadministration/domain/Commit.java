package io.reflectoring.coderadar.projectadministration.domain;

import java.util.List;
import lombok.Data;
import lombok.ToString;

/** Metadata about a commit to a Git repository. */
@Data
public class Commit {

  private long id;
  private String name;
  private long timestamp;
  private String comment;
  private String author;
  private String authorEmail;
  private boolean analyzed;

  @ToString.Exclude private List<Commit> parents;

  @ToString.Exclude private List<FileToCommitRelationship> touchedFiles;
}
