package io.reflectoring.coderadar.projectadministration.domain;

import java.util.List;
import lombok.Data;
import lombok.ToString;

/** Metadata about a commit to a Git repository. */
@Data
public class Commit {
  private long id;
  private long timestamp;
  private boolean analyzed;
  private String hash;
  private String comment;
  private String author;
  private String authorEmail;

  @ToString.Exclude private List<Commit> parents;

  @ToString.Exclude private List<FileToCommitRelationship> touchedFiles;
}
