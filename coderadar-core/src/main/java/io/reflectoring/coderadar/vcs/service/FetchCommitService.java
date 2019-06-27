package io.reflectoring.coderadar.vcs.service;

import io.reflectoring.coderadar.vcs.UnableToFetchCommitException;
import io.reflectoring.coderadar.vcs.port.driven.FetchCommitPort;
import io.reflectoring.coderadar.vcs.port.driver.fetchcommit.FetchCommitCommand;
import io.reflectoring.coderadar.vcs.port.driver.fetchcommit.FetchCommitUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FetchCommitService implements FetchCommitUseCase {

  private final FetchCommitPort fetchCommitPort;

  @Autowired
  public FetchCommitService(FetchCommitPort fetchCommitPort) {
    this.fetchCommitPort = fetchCommitPort;
  }

  @Override
  public void fetchCommit(FetchCommitCommand command) throws UnableToFetchCommitException {
    fetchCommitPort.fetchCommit(command);
  }
}
