package io.reflectoring.coderadar.vcs.port.driven;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.query.domain.DateRange;
import java.nio.file.Path;
import java.util.List;

public interface GetProjectCommitsPort {
  List<Commit> getCommits(Path repositoryRoot, DateRange range);
}
