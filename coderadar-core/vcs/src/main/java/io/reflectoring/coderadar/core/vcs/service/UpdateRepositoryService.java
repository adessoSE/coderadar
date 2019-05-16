package io.reflectoring.coderadar.core.vcs.service;

import io.reflectoring.coderadar.core.vcs.port.driven.UpdateRepositoryPort;
import io.reflectoring.coderadar.core.vcs.port.driver.ResetRepositoryUseCase;
import io.reflectoring.coderadar.core.vcs.port.driver.UpdateRepositoryUseCase;
import java.io.IOException;
import java.nio.file.Path;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("UpdateRepositoryService")
public class UpdateRepositoryService implements UpdateRepositoryUseCase {

  public final UpdateRepositoryPort updateRepositoryPort;

  private ResetRepositoryUseCase resetter;

  @Autowired
  public UpdateRepositoryService(
      @Qualifier("UpdateRepositoryServiceNeo4j") UpdateRepositoryPort updateRepositoryPort,
      ResetRepositoryUseCase resetter) {
    this.resetter = resetter;
    this.updateRepositoryPort = updateRepositoryPort;
  }

  @Override
  public Git updateRepository(Path repositoryRoot) {
    try {
      return updateInternal(repositoryRoot);
    } catch (CheckoutConflictException e) {
      // When having a checkout conflict, someone or something fiddled with the working directory.
      // Since the working directory is designed to be read only, we just revert it and try again.
      resetter.resetRepository(repositoryRoot);
      try {
        return updateInternal(repositoryRoot);
      } catch (Exception e2) {
        throw createException(e2, repositoryRoot);
      }
    } catch (Exception e) {
      throw createException(e, repositoryRoot);
    }
  }

  private IllegalStateException createException(Exception cause, Path repositoryRoot) {
    return new IllegalStateException(
        String.format("error accessing local GIT repository at %s", repositoryRoot), cause);
  }

  private Git updateInternal(Path repositoryRoot) throws GitAPIException, IOException {
    FileRepositoryBuilder builder = new FileRepositoryBuilder();
    Repository repository = builder.setWorkTree(repositoryRoot.toFile()).build();
    Git git = new Git(repository);
    git.pull().setStrategy(MergeStrategy.THEIRS).call();
    updateRepositoryPort.updateRepository(repositoryRoot);
    return git;
  }
}
