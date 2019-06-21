package io.reflectoring.coderadar.vcs.port.driver;

import io.reflectoring.coderadar.query.domain.DateRange;
import java.nio.file.Path;

public interface SaveProjectCommitsUseCase {
  void saveCommits(Path repositoryRoot, DateRange range);
}
