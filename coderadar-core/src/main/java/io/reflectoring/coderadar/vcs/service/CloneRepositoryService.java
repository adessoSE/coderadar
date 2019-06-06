package io.reflectoring.coderadar.vcs.service;

import io.reflectoring.coderadar.vcs.port.driven.CloneRepositoryPort;
import io.reflectoring.coderadar.vcs.port.driver.CloneRepositoryCommand;
import io.reflectoring.coderadar.vcs.port.driver.CloneRepositoryUseCase;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("CloneRepositoryService")
public class CloneRepositoryService implements CloneRepositoryUseCase {

  private final CloneRepositoryPort cloneRepositoryPort;

  @Autowired
  public CloneRepositoryService(
      @Qualifier("CloneRepositoryServiceNeo4j") CloneRepositoryPort cloneRepositoryPort) {
    this.cloneRepositoryPort = cloneRepositoryPort;
  }

  @Override
  public Git cloneRepository(CloneRepositoryCommand command) {
    cloneRepositoryPort.cloneRepository(command.getRemoteUrl(), command.getLocalDir());
    try {
      // TODO: support cloning with credentials for private repositories
      // TODO: support progress monitoring
      Git git =
          Git.cloneRepository()
              .setURI(command.getRemoteUrl())
              .setDirectory(command.getLocalDir())
              .call();
      git.getRepository().close();
      return git;
    } catch (GitAPIException e) {
      throw new RuntimeException(e);
    }
  }
}
