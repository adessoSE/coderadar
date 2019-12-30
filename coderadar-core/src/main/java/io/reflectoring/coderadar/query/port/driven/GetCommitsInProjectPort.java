package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import java.util.List;

public interface GetCommitsInProjectPort {

  List<Commit> getCommitsSortedByTimestampDescWithNoRelationships(Long projectId);

  List<Commit> getSortedByTimestampAsc(Long projectId);

  List<Commit> getNonanalyzedSortedByTimestampAscWithNoParents(
      Long projectId, List<FilePattern> filePatterns);
}
