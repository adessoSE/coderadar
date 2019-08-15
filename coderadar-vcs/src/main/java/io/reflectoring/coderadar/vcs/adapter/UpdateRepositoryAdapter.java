package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.vcs.UnableToResetRepositoryException;
import io.reflectoring.coderadar.vcs.UnableToUpdateRepositoryException;
import io.reflectoring.coderadar.vcs.port.driven.UpdateRepositoryPort;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class UpdateRepositoryAdapter implements UpdateRepositoryPort {
  private ResetRepositoryAdapter resetRepositoryAdapter;

  @Autowired
  public UpdateRepositoryAdapter(ResetRepositoryAdapter resetRepositoryAdapter) {
    this.resetRepositoryAdapter = resetRepositoryAdapter;
  }

  @Override
  public void updateRepository(Path repositoryRoot) throws UnableToUpdateRepositoryException {
    try {
      updateInternal(repositoryRoot);
    } catch (CheckoutConflictException e) {
      // When having a checkout conflict, someone or something fiddled with the working directory.
      // Since the working directory is designed to be read only, we just revert it and try again.
      try {
        resetRepositoryAdapter.resetRepository(repositoryRoot);
        updateInternal(repositoryRoot);
      } catch (UnableToResetRepositoryException | IOException | GitAPIException ex) {
        throw createException(repositoryRoot, e.getMessage());
      }
    } catch (IOException | GitAPIException e) {
      throw createException(repositoryRoot, e.getMessage());
    }
  }

  private UnableToUpdateRepositoryException createException(Path repositoryRoot, String error) {
    return new UnableToUpdateRepositoryException(
        String.format(
            "Error updating local GIT repository at %s. Reason: %s", repositoryRoot, error));
  }

  private Git updateInternal(Path repositoryRoot) throws GitAPIException, IOException {
    FileRepositoryBuilder builder = new FileRepositoryBuilder();
    Repository repository = builder.setWorkTree(repositoryRoot.toFile()).build();
    Git git = new Git(repository);
    git.pull().setStrategy(MergeStrategy.THEIRS).call();
    return git;
  }
}
