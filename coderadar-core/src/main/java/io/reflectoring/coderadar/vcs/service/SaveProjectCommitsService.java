package io.reflectoring.coderadar.vcs.service;

import io.reflectoring.coderadar.query.domain.DateRange;
import io.reflectoring.coderadar.vcs.port.driven.SaveProjectCommitsPort;
import io.reflectoring.coderadar.vcs.port.driver.SaveProjectCommitsUseCase;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveProjectCommitsService implements SaveProjectCommitsUseCase {

  private final SaveProjectCommitsPort saveProjectCommitsPort;

  @Autowired
  public SaveProjectCommitsService(SaveProjectCommitsPort saveProjectCommitsPort) {
    this.saveProjectCommitsPort = saveProjectCommitsPort;
  }

  @Override
  public void saveCommits(Path repositoryRoot, DateRange range) {
    saveProjectCommitsPort.saveCommits(repositoryRoot, range);
  }
}
