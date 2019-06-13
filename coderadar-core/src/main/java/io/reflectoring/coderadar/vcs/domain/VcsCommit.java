package io.reflectoring.coderadar.vcs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A data class representing a commit in a local git repository. (Replaces the old JGit RevCommit
 * class)
 */
@Data
@AllArgsConstructor
public class VcsCommit {
  private int commitTime;
  private String name;
  private String author;
  private String message;
  private final int sequenceNumber;
}
