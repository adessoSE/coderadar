package io.reflectoring.coderadar.vcs.port.driver;

import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.query.domain.DateRange;

import java.io.File;
import java.util.List;

public interface ExtractProjectCommitsUseCase {
  List<Commit> getCommits(File repositoryRoot, DateRange range);
}
