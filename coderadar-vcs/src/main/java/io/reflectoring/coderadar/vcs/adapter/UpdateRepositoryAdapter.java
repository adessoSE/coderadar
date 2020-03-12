package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.vcs.UnableToCloneRepositoryException;
import io.reflectoring.coderadar.vcs.UnableToUpdateRepositoryException;
import io.reflectoring.coderadar.vcs.port.driven.UpdateRepositoryPort;
import io.reflectoring.coderadar.vcs.port.driver.clone.CloneRepositoryCommand;
import io.reflectoring.coderadar.vcs.port.driver.update.UpdateRepositoryCommand;
import java.io.File;
import java.io.IOException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.eclipse.jgit.api.FetchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;

@Service
public class UpdateRepositoryAdapter implements UpdateRepositoryPort {

  private final CloneRepositoryAdapter cloneRepositoryAdapter;

  public UpdateRepositoryAdapter(CloneRepositoryAdapter cloneRepositoryAdapter) {
    this.cloneRepositoryAdapter = cloneRepositoryAdapter;
  }

  @Override
  public boolean updateRepository(UpdateRepositoryCommand command)
      throws UnableToUpdateRepositoryException {
    try {
      return updateInternal(command);
    } catch (CheckoutConflictException e) {
      // When having a checkout conflict, someone or something fiddled with the working directory.
      // Since the working directory is designed to be read only, we just revert it and try again.
      try {
        resetRepository(command.getLocalDir());
        return updateInternal(command);
      } catch (IOException | GitAPIException | UnableToCloneRepositoryException ex) {
        throw createException(command.getLocalDir(), e.getMessage());
      }
    } catch (IOException | GitAPIException | UnableToCloneRepositoryException e) {
      throw createException(command.getLocalDir(), e.getMessage());
    }
  }

  private UnableToUpdateRepositoryException createException(String repositoryRoot, String error) {
    return new UnableToUpdateRepositoryException(
        String.format(
            "Error updating local GIT repository at %s. Reason: %s", repositoryRoot, error));
  }

  private boolean updateInternal(UpdateRepositoryCommand command)
      throws GitAPIException, IOException, UnableToCloneRepositoryException {
    FileRepositoryBuilder builder = new FileRepositoryBuilder();
    Repository repository = builder.setWorkTree(new File(command.getLocalDir())).build();
    Git git = new Git(repository);
    ObjectId oldHead = git.getRepository().resolve(Constants.HEAD);
    if (oldHead == null) {
      git.close();
      deleteAndCloneRepository(command);
      return true;
    }
    StoredConfig config = git.getRepository().getConfig();
    config.setString("remote", "origin", "url", command.getRemoteUrl());
    config.save();
    FetchCommand fetchCommand = git.fetch();
    if (command.getUsername() != null && command.getPassword() != null) {
      fetchCommand.setCredentialsProvider(
          new UsernamePasswordCredentialsProvider(command.getUsername(), command.getPassword()));
    }
    fetchCommand.call();
    try {
      git.checkout().setName("origin/master").setForce(true).call();
    } catch (JGitInternalException e) {
      git.close();
      deleteAndCloneRepository(command);
      return false;
    }
    git.reset().setMode(ResetCommand.ResetType.HARD).setRef("origin/master").call();
    ObjectId newHead = git.getRepository().resolve(Constants.HEAD);
    git.getRepository().close();
    git.close();
    return !oldHead.equals(newHead);
  }

  private void deleteAndCloneRepository(UpdateRepositoryCommand command)
      throws UnableToCloneRepositoryException, IOException {
    File localDir = new File(command.getLocalDir());
    if (localDir.exists()) {
      FileUtils.forceDelete(localDir);
    }
    cloneRepositoryAdapter.cloneRepository(
        new CloneRepositoryCommand(
            command.getRemoteUrl(),
            command.getLocalDir(),
            command.getUsername(),
            command.getPassword()));
  }

  private void resetRepository(String repositoryRoot) throws IOException, GitAPIException {
    FileRepositoryBuilder builder = new FileRepositoryBuilder();
    Repository repository;
    repository = builder.setWorkTree(new File(repositoryRoot)).build();
    Git git = new Git(repository);
    git.reset().setMode(ResetCommand.ResetType.HARD).call();
    git.getRepository().close();
    git.close();
  }
}
