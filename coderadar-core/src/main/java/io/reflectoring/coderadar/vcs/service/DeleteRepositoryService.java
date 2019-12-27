package io.reflectoring.coderadar.vcs.service;

import io.reflectoring.coderadar.vcs.port.driven.DeleteRepositoryPort;
import io.reflectoring.coderadar.vcs.port.driver.DeleteRepositoryUseCase;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteRepositoryService implements DeleteRepositoryUseCase {

  private DeleteRepositoryPort deleteRepositoryPort;

  @Autowired
  public DeleteRepositoryService(DeleteRepositoryPort deleteRepositoryPort) {
    this.deleteRepositoryPort = deleteRepositoryPort;
  }

  @Override
  public void deleteRepository(String repositoryPath) throws IOException {
    deleteRepositoryPort.deleteRepository(repositoryPath);
  }
}
