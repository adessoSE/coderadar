package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.query.domain.CommitLog;
import java.util.List;

public interface GetCommitLogPort {
  List<CommitLog> getCommitLog(String path);
}
