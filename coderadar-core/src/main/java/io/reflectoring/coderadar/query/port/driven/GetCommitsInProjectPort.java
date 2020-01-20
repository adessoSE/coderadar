package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GetCommitsInProjectPort {

  List<Commit> getCommitsSortedByTimestampDescWithNoRelationships(Long projectId);

  Page<Commit> getCommitsSortedByTimestampDescWithNoRelationshipsPaged(Long projectId, Pageable pageRequest);

  List<Commit> getSortedByTimestampAsc(Long projectId);

  List<Commit> getNonanalyzedSortedByTimestampAscWithNoParents(
      Long projectId, List<FilePattern> filePatterns);
}
