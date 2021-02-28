package io.reflectoring.coderadar.projectadministration.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/** Metadata about a commit to a Git repository. */
@Data
public class Commit {
  private long id;
  private long timestamp;
  private long hash;
  private boolean analyzed;
  private String comment;
  private String author;
  private String authorEmail;

  @ToString.Exclude @EqualsAndHashCode.Exclude
  private List<Commit> parents = Collections.emptyList();

  @ToString.Exclude @EqualsAndHashCode.Exclude private List<File> changedFiles = new ArrayList<>();

  @ToString.Exclude @EqualsAndHashCode.Exclude private List<File> deletedFiles = new ArrayList<>();
}
