package io.reflectoring.coderadar.vcs.service;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.vcs.port.driven.FindCommitPort;
import io.reflectoring.coderadar.vcs.port.driver.FindCommitUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class FindCommitService implements FindCommitUseCase {

  private final FindCommitPort findCommitPort;

  @Autowired
  public FindCommitService(FindCommitPort findCommitPort) {
    this.findCommitPort = findCommitPort;
  }

  @Override
  public Commit findCommit(Path repositoryRoot, String name) {
    return findCommitPort.findCommit(repositoryRoot, name);
  }
}
