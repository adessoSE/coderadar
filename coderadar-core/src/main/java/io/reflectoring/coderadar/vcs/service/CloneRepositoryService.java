package io.reflectoring.coderadar.vcs.service;

import io.reflectoring.coderadar.vcs.UnableToCloneRepositoryException;
import io.reflectoring.coderadar.vcs.port.driven.CloneRepositoryPort;
import io.reflectoring.coderadar.vcs.port.driver.clone.CloneRepositoryCommand;
import io.reflectoring.coderadar.vcs.port.driver.clone.CloneRepositoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CloneRepositoryService implements CloneRepositoryUseCase {

  private final CloneRepositoryPort cloneRepositoryPort;

  @Override
  public void cloneRepository(CloneRepositoryCommand command)
      throws UnableToCloneRepositoryException {
    cloneRepositoryPort.cloneRepository(command);
  }
}
