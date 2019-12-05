package io.reflectoring.coderadar.vcs.service;

import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.query.domain.DateRange;
import io.reflectoring.coderadar.vcs.port.driven.ExtractProjectCommitsPort;
import io.reflectoring.coderadar.vcs.port.driver.ExtractProjectCommitsUseCase;
import java.nio.file.Path;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ExtractProjectCommitsService implements ExtractProjectCommitsUseCase {

  private final ExtractProjectCommitsPort extractProjectCommitsPort;

  public ExtractProjectCommitsService(ExtractProjectCommitsPort extractProjectCommitsPort) {
    this.extractProjectCommitsPort = extractProjectCommitsPort;
  }

  @Override
  public List<Commit> getCommits(Path repositoryRoot, DateRange range) {
    return extractProjectCommitsPort.getCommits(repositoryRoot, range);
  }
}
