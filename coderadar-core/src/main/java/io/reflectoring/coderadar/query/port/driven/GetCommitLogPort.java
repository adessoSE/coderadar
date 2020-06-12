package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.query.domain.CommitLog;

import java.util.List;

public interface GetCommitLogPort {

  /**
   * @param projectId The id of the project.
   * @return A list of commits in the repository with the given path in a subset of the git2json
   *     format.
   */
  List<CommitLog> getCommitLog(long projectId);
}
