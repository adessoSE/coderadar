package io.reflectoring.coderadar.vcs.service;

import io.reflectoring.coderadar.vcs.UnableToResetRepositoryException;
import io.reflectoring.coderadar.vcs.port.driven.ResetRepositoryPort;
import io.reflectoring.coderadar.vcs.port.driver.ResetRepositoryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class ResetRepositoryService implements ResetRepositoryUseCase {

  private final ResetRepositoryPort resetRepositoryPort;

  @Autowired
  public ResetRepositoryService(ResetRepositoryPort resetRepositoryPort) {
    this.resetRepositoryPort = resetRepositoryPort;
  }

  @Override
  public void resetRepository(Path repositoryRoot) throws UnableToResetRepositoryException {
    resetRepositoryPort.resetRepository(repositoryRoot);
  }
}
