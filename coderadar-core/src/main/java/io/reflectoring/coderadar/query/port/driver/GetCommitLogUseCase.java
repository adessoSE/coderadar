package io.reflectoring.coderadar.query.port.driver;

import io.reflectoring.coderadar.query.domain.CommitLog;
import java.util.List;

public interface GetCommitLogUseCase {
  List<CommitLog> getCommitLog(long projectId);
}
