package io.reflectoring.coderadar.query.port.driver;

import io.reflectoring.coderadar.query.domain.CommitLog;

import java.util.List;

public interface GetCommitLogUseCase {

  /**
   * @param projectId The project id.
   * @return A list of commits in the project with the given id in a subset of the git2json format.
   */
  List<CommitLog> getCommitLog(long projectId);
}
