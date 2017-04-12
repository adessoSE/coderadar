package org.wickedsource.coderadar.graph.service;

import java.time.LocalDateTime;

public class GitChange {

  private final String commitName;

  private final String parentCommitName;

  private final String filepath;

  private final String oldFilepath;

  private final ChangeType changeType;

  private final LocalDateTime timestamp;

  private final LocalDateTime parentTimestamp;

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
      LocalDateTime timestamp,
      LocalDateTime parentTimestamp) {
    this.commitName = commitName;
    this.filepath = filepath;
    this.parentCommitName = parentCommitName;
    this.oldFilepath = oldFilepath;
    this.changeType = changeType;
    this.timestamp = timestamp;
    this.parentTimestamp = parentTimestamp;
  }

  public String getCommitName() {
    return commitName;
  }

  public String getFilepath() {
    return filepath;
  }

  public String getOldFilepath() {
    return oldFilepath;
  }

  public ChangeType getChangeType() {
    return changeType;
  }

  public String getParentCommitName() {
    return parentCommitName;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public LocalDateTime getParentTimestamp() {
    return parentTimestamp;
  }
}
