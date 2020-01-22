package io.reflectoring.coderadar.projectadministration.domain;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/** Metadata about a commit to a Git repository. */
@Data
public class Commit {
  private Long id;
  private String name;
  private Long timestamp;
  private String comment;
  private String author;
  private boolean analyzed = false;

  @ToString.Exclude private List<Commit> parents = new ArrayList<>();

  @ToString.Exclude private List<FileToCommitRelationship> touchedFiles = new ArrayList<>();
}
