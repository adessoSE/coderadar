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
  protected int commitTime;
  protected String name;
  protected String author;
  protected String message;
  protected final int sequenceNumber;
}
