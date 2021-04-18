package io.reflectoring.coderadar.vcs.port.driver;

import io.reflectoring.coderadar.domain.Commit;
import io.reflectoring.coderadar.domain.DateRange;
import java.io.IOException;
import java.util.List;

public interface ExtractProjectCommitsUseCase {
  List<Commit> getCommits(String repositoryRoot, DateRange range) throws IOException;
}
