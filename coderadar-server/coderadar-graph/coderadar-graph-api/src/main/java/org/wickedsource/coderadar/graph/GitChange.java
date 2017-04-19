package org.wickedsource.coderadar.graph;

import java.time.LocalDateTime;

/**
 * A {@link GitChange} describes a change within a git repository. A change means that a file has
 * been ADDED, MODIFIED, DELETED or RENAMED within a commit.
 */
public class GitChange {

  private final String commitName;

  private final String parentCommitName;

  private final String filepath;

  private final String oldFilepath;

  private final ChangeType changeType;

  private final LocalDateTime timestamp;

  public enum ChangeType {
    ADDED,
    MODIFIED,
    DELETED,
    RENAMED
  }

  public GitChange(
      String commitName,
      String parentCommitName,
      String filepath,
      String oldFilepath,
      ChangeType changeType,
      LocalDateTime timestamp) {
    this.commitName = commitName;
    this.filepath = filepath;
    this.parentCommitName = parentCommitName;
    this.oldFilepath = oldFilepath;
    this.changeType = changeType;
    this.timestamp = timestamp;
  }

  /** The name of the commit in which this change took place. */
  public String getCommitName() {
    return commitName;
  }

  /** The path of the file that was touched within this change. */
  public String getFilepath() {
    return filepath;
  }

  /**
   * The filepath before a RENAME operation. The {@code oldFilepath} attribute only differs from the
   * {@code filepath} attribute if the {@code changeType} of this {@link GitChange} is RENAME.
   */
  public String getOldFilepath() {
    return oldFilepath;
  }

  /** The type of change to the file that was touched within this change. */
  public ChangeType getChangeType() {
    return changeType;
  }

  /** The name of the parent commit this change originates from. */
  public String getParentCommitName() {
    return parentCommitName;
  }

  /** The date and time of when the change was committed to the git repository. */
  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {
    return String.format("GitChange[changeType=%s;filepath=%s]", this.changeType, this.filepath);
  }
}
