package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.vcs.UnableToUpdateRepositoryException;
import io.reflectoring.coderadar.vcs.port.driven.UpdateRepositoryPort;
import io.reflectoring.coderadar.vcs.port.driver.update.UpdateRepositoryCommand;
import java.io.IOException;
import java.nio.file.Path;
import org.eclipse.jgit.api.FetchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;

@Service
public class UpdateRepositoryAdapter implements UpdateRepositoryPort {

  @Override
  public boolean updateRepository(UpdateRepositoryCommand command)
      throws UnableToUpdateRepositoryException {
    try {
      return updateInternal(command);
    } catch (CheckoutConflictException e) {
      // When having a checkout conflict, someone or something fiddled with the working directory.
      // Since the working directory is designed to be read only, we just revert it and try again.
      try {
        resetRepository(command.getLocalDir().toPath());
        return updateInternal(command);
      } catch (IOException | GitAPIException ex) {
        throw createException(command.getLocalDir().toPath(), e.getMessage());
      }
    } catch (IOException | GitAPIException e) {
      throw createException(command.getLocalDir().toPath(), e.getMessage());
    }
  }

  private UnableToUpdateRepositoryException createException(Path repositoryRoot, String error) {
    return new UnableToUpdateRepositoryException(
        String.format(
            "Error updating local GIT repository at %s. Reason: %s", repositoryRoot, error));
  }

  private boolean updateInternal(UpdateRepositoryCommand command)
      throws GitAPIException, IOException {
    FileRepositoryBuilder builder = new FileRepositoryBuilder();
    Repository repository = builder.setWorkTree(command.getLocalDir()).build();
    Git git = new Git(repository);
    StoredConfig config = git.getRepository().getConfig();
    config.setString("remote", "origin", "url", command.getRemoteUrl());
    config.save();
    ObjectId oldHead = git.getRepository().resolve(Constants.HEAD);
    FetchCommand fetchCommand = git.fetch();
    if (command.getUsername() != null && command.getPassword() != null) {
      fetchCommand.setCredentialsProvider(
          new UsernamePasswordCredentialsProvider(command.getUsername(), command.getPassword()));
    }
    fetchCommand.call();
    git.checkout().setName("origin/master").setForce(true).call();
    git.reset().setMode(ResetCommand.ResetType.HARD).setRef("origin/master").call();
    ObjectId newHead = git.getRepository().resolve(Constants.HEAD);
    git.getRepository().close();
    git.close();
    return (oldHead == null && newHead != null) || (oldHead != null && !oldHead.equals(newHead));
  }

  private void resetRepository(Path repositoryRoot) throws IOException, GitAPIException {
    FileRepositoryBuilder builder = new FileRepositoryBuilder();
    Repository repository;
    repository = builder.setWorkTree(repositoryRoot.toFile()).build();
    Git git = new Git(repository);
    git.reset().setMode(ResetCommand.ResetType.HARD).call();
    git.getRepository().close();
    git.close();
  }
}
