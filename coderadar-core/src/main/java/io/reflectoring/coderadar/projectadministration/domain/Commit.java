package io.reflectoring.coderadar.projectadministration.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.Data;
import lombok.ToString;

/** Metadata about a commit to a Git repository. */
@Data
public class Commit {

  @JsonIgnore private long id;

  private String name;
  private long timestamp;
  private String comment;
  private String author;
  private String authorEmail;
  private boolean analyzed = false;

  @JsonIgnore @ToString.Exclude private List<Commit> parents;

  @JsonIgnore @ToString.Exclude private List<FileToCommitRelationship> touchedFiles;
}
