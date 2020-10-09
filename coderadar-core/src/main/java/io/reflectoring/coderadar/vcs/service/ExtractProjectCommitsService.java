package io.reflectoring.coderadar.vcs.service;

import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.query.domain.DateRange;
import io.reflectoring.coderadar.vcs.port.driven.ExtractProjectCommitsPort;
import io.reflectoring.coderadar.vcs.port.driver.ExtractProjectCommitsUseCase;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExtractProjectCommitsService implements ExtractProjectCommitsUseCase {

  private final ExtractProjectCommitsPort extractProjectCommitsPort;

  @Override
  public List<Commit> getCommits(String repositoryRoot, DateRange range) throws IOException {
    return extractProjectCommitsPort.extractCommits(repositoryRoot, range);
  }
}
