package io.reflectoring.coderadar.vcs.port.driver;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.query.domain.DateRange;

import java.nio.file.Path;
import java.util.List;

public interface GetProjectCommitsUseCase {
  List<Commit> getCommits(Path repositoryRoot, DateRange range);
}
