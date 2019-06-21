package io.reflectoring.coderadar.vcs.port.driven;

import io.reflectoring.coderadar.query.domain.DateRange;
import java.nio.file.Path;

public interface SaveProjectCommitsPort {
  void saveCommits(Path repositoryRoot, DateRange range);
}
