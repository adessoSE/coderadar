package io.reflectoring.coderadar.vcs.service;

import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.query.domain.DateRange;
import io.reflectoring.coderadar.vcs.port.driven.GetProjectCommitsPort;
import io.reflectoring.coderadar.vcs.port.driver.GetProjectCommitsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class GetProjectCommitsService implements GetProjectCommitsUseCase {

  private final GetProjectCommitsPort getProjectCommitsPort;

  @Autowired
  public GetProjectCommitsService(GetProjectCommitsPort getProjectCommitsPort) {
    this.getProjectCommitsPort = getProjectCommitsPort;
  }

  @Override
  public List<Commit> getCommits(Path repositoryRoot, DateRange range) {
    return getProjectCommitsPort.getCommits(repositoryRoot, range);
  }
}
