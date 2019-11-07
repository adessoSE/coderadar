package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.query.port.driver.GetCommitResponse;
import java.util.List;

public interface GetCommitsInProjectPort {
  List<Commit> get(Long projectId);

  List<GetCommitResponse> getCommitsResponseSortedByTimestampDesc(Long projectId);

  List<Commit> getSortedByTimestampAsc(Long projectId);
}
