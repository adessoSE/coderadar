package io.reflectoring.coderadar.projectadministration.domain;

import io.reflectoring.coderadar.plugin.api.ChangeType;
import lombok.Data;

/**
 * Associates a Commit to a File. Each Commit is associated to all Files that have been modified in
 * the Commit and to all files that have been left untouched by it, so that one can easily access
 * the full set of files at the time of the Commit.
 */
@Data
public class FileToCommitRelationship {
  private ChangeType changeType;
  private File file;
}
