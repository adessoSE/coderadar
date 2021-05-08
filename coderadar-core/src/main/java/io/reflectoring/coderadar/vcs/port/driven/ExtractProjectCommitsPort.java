package io.reflectoring.coderadar.vcs.port.driven;

import io.reflectoring.coderadar.domain.Commit;
import io.reflectoring.coderadar.domain.DateRange;
import java.io.IOException;
import java.util.List;

public interface ExtractProjectCommitsPort {

  /**
   * @param repositoryRoot The local git repository.
   * @param range The date range to use when extracting.
   * @return A complete "Commit tree" (Commits with FileToCommitRelationships and Files)
   */
  List<Commit> extractCommits(String repositoryRoot, DateRange range) throws IOException;
}
