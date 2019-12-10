package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.query.port.driver.GetCommitResponse;
import java.util.List;

public interface GetCommitsInProjectPort {

  List<GetCommitResponse> getCommitsResponseSortedByTimestampDesc(Long projectId);

  List<Commit> getSortedByTimestampAsc(Long projectId);

  List<Commit> getSortedByTimestampAscWithNoParents(Long projectId);

  List<Commit> getNonanalyzedSortedByTimestampAscWithNoParents(Long projectId);
}
