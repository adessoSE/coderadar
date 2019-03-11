package org.wickedsource.coderadar.graph;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A {@link GitChange} describes a change within a git repository. A change means that a file has
 * been ADDED, MODIFIED, DELETED or RENAMED within a commit.
 */
@AllArgsConstructor
@Data
public class GitChange {

  /** The name of the commit in which this change took place. */
  private final String commitName;

  /** The name of the parent commit this change originates from. */
  private final String parentCommitName;

  /** The path of the file that was touched within this change. */
  private final String filepath;

  /**
   * The filepath before a RENAME operation. The {@code oldFilepath} attribute only differs from the
   * {@code filepath} attribute if the {@code changeType} of this {@link GitChange} is RENAME.
   */
  private final String oldFilepath;

  /** The type of change to the file that was touched within this change. */
  private final ChangeType changeType;

  /** The date and time of when the change was committed to the git repository. */
  private final LocalDateTime timestamp;

  public enum ChangeType {
    ADDED,
    MODIFIED,
    DELETED,
    RENAMED
  }

  @Override
  public String toString() {
    return String.format("GitChange[changeType=%s;filepath=%s]", this.changeType, this.filepath);
  }
}
