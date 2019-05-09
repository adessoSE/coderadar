package io.reflectoring.coderadar.core.vcs.service;

import io.reflectoring.coderadar.core.vcs.port.driven.ResetRepositoryPort;
import io.reflectoring.coderadar.core.vcs.port.driver.ResetRepositoryUseCase;
import java.nio.file.Path;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResetRepositoryService implements ResetRepositoryUseCase {

  private final ResetRepositoryPort resetRepositoryPort;

  @Autowired
  public ResetRepositoryService(ResetRepositoryPort resetRepositoryPort) {
    this.resetRepositoryPort = resetRepositoryPort;
  }

  @Override
  public Git resetRepository(Path repositoryRoot) {
    try {
      FileRepositoryBuilder builder = new FileRepositoryBuilder();
      Repository repository = builder.setWorkTree(repositoryRoot.toFile()).build();
      Git git = new Git(repository);
      git.reset().setMode(ResetCommand.ResetType.HARD).call();
      // TODO: Update Repository in graph DB using port
      return git;
    } catch (Exception e) {
      throw new IllegalStateException(
          String.format("error resetting local GIT repository at %s", repositoryRoot), e);
    }
  }
}
