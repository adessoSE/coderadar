package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import java.util.List;

public interface GetCommitsInProjectPort {
  List<Commit> getSortedByTimestampDesc(Long projectId);
  List<Commit> get(Long projectId);
}
