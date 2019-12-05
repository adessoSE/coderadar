package io.reflectoring.coderadar.vcs.port.driven;

import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.query.domain.DateRange;
import java.nio.file.Path;
import java.util.List;

public interface ExtractProjectCommitsPort {

  /**
   * @param repositoryRoot The name of the local git repository.
   * @param range The date range to use when extracting.
   * @return A complete "Commit tree" (Commits with FileToCommitRelationships and Files)
   */
  List<Commit> getCommits(Path repositoryRoot, DateRange range);
}
