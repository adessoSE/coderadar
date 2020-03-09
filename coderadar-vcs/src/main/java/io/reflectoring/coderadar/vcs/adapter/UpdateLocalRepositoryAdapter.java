package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.projectadministration.domain.Branch;
import io.reflectoring.coderadar.vcs.UnableToCloneRepositoryException;
import io.reflectoring.coderadar.vcs.UnableToUpdateRepositoryException;
import io.reflectoring.coderadar.vcs.port.driven.UpdateLocalRepositoryPort;
import io.reflectoring.coderadar.vcs.port.driver.clone.CloneRepositoryCommand;
import io.reflectoring.coderadar.vcs.port.driver.update.UpdateRepositoryCommand;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.tomcat.util.http.fileupload.FileUtils;
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
public class UpdateLocalRepositoryAdapter implements UpdateLocalRepositoryPort {

  private final CloneRepositoryAdapter cloneRepositoryAdapter;
  private final GetAvailableBranchesAdapter getAvailableBranchesAdapter;

  public UpdateLocalRepositoryAdapter(
      CloneRepositoryAdapter cloneRepositoryAdapter,
      GetAvailableBranchesAdapter getAvailableBranchesAdapter) {
    this.cloneRepositoryAdapter = cloneRepositoryAdapter;
    this.getAvailableBranchesAdapter = getAvailableBranchesAdapter;
  }

  @Override
  public List<Branch> updateRepository(UpdateRepositoryCommand command)
      throws UnableToUpdateRepositoryException {
    try {
      return updateInternal(command);
    } catch (CheckoutConflictException e) {
      // When having a checkout conflict, someone or something fiddled with the working directory.
      // Since the working directory is designed to be read only, we just revert it and try again.
      try {
        resetRepository(command.getLocalDir().toPath());
        return updateInternal(command);
      } catch (IOException | GitAPIException | UnableToCloneRepositoryException ex) {
        throw createException(command.getLocalDir().toPath(), e.getMessage());
      }
    } catch (IOException | GitAPIException | UnableToCloneRepositoryException e) {
      throw createException(command.getLocalDir().toPath(), e.getMessage());
    }
  }

  private UnableToUpdateRepositoryException createException(Path repositoryRoot, String error) {
    return new UnableToUpdateRepositoryException(
        String.format(
            "Error updating local GIT repository at %s. Reason: %s", repositoryRoot, error));
  }

  private List<Branch> updateInternal(UpdateRepositoryCommand command)
      throws GitAPIException, IOException, UnableToCloneRepositoryException {
    FileRepositoryBuilder builder = new FileRepositoryBuilder();
    Repository repository =
        builder
            .setWorkTree(command.getLocalDir())
            .setBare()
            .setGitDir(command.getLocalDir())
            .build();
    Git git = new Git(repository);
    ObjectId oldHead = git.getRepository().resolve(Constants.HEAD);
    if (oldHead == null) {
      git.close();
      deleteAndCloneRepository(command);
      return getAvailableBranchesAdapter.getAvailableBranches(command.getLocalDir());
    }
    StoredConfig config = git.getRepository().getConfig();
    config.setString("remote", "origin", "url", command.getRemoteUrl());
    config.save();
    FetchCommand fetchCommand = git.fetch().setRemoveDeletedRefs(true);
    if (command.getUsername() != null && command.getPassword() != null) {
      fetchCommand.setCredentialsProvider(
          new UsernamePasswordCredentialsProvider(command.getUsername(), command.getPassword()));
    }
    List<Branch> updatedBranches = new ArrayList<>();
    fetchCommand
        .call()
        .getTrackingRefUpdates()
        .forEach(
            trackingRefUpdate -> {
              if (!trackingRefUpdate.getNewObjectId().equals(trackingRefUpdate.getOldObjectId())) {
                String[] branchName = trackingRefUpdate.getLocalName().split("/");
                int length = branchName.length;
                String truncatedName = branchName[length - 1];
                updatedBranches.add(
                    new Branch()
                        .setName(truncatedName)
                        .setCommitHash(trackingRefUpdate.getNewObjectId().getName()));
              }
            });
    git.getRepository().close();
    git.close();
    return updatedBranches;
  }

  private void deleteAndCloneRepository(UpdateRepositoryCommand command)
      throws UnableToCloneRepositoryException, IOException {
    if (command.getLocalDir().exists()) {
      FileUtils.forceDelete(command.getLocalDir());
    }
    cloneRepositoryAdapter.cloneRepository(
        new CloneRepositoryCommand(
            command.getRemoteUrl(),
            command.getLocalDir(),
            command.getUsername(),
            command.getPassword()));
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
