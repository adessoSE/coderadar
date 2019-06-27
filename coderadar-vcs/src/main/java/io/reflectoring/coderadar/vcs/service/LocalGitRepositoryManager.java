package io.reflectoring.coderadar.vcs.service;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.vcs.adapter.UpdateRepositoryAdapter;
import io.reflectoring.coderadar.vcs.port.driver.clone.CloneRepositoryCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class LocalGitRepositoryManager {

  private final UpdateRepositoryAdapter updateRepositoryAdapter;

  private final CloneRepositoryService cloneRepositoryService;

  private final CheckRepositoryService checkRepositoryService;

  private final WorkdirManager workdirManager;

  @Autowired
  public LocalGitRepositoryManager(
      UpdateRepositoryAdapter updateRepositoryService,
      CloneRepositoryService cloneRepositoryService,
      CheckRepositoryService checkRepositoryService,
      WorkdirManager workdirManager) {
    this.updateRepositoryAdapter = updateRepositoryService;
    this.cloneRepositoryService = cloneRepositoryService;
    this.checkRepositoryService = checkRepositoryService;
    this.workdirManager = workdirManager;
  }

  public Git getLocalGitRepository(long projectId) {
    Path workdir = getWorkdir(projectId);
    if (!isRepositoryAlreadyCheckedOut(projectId)) {
      throw new IllegalArgumentException(
          String.format("no local git repository found at %s", workdir));
    }

    try {
      return Git.open(workdir.toFile());
    } catch (IOException e) {
      throw new IllegalStateException(
          String.format("could not open local git repository at %s due to exception", workdir), e);
    }
  }

  public Git updateLocalGitRepository(Project project) throws GitAPIException, IOException {
    if (project == null) {
      throw new IllegalArgumentException("parameter project must not be null!");
    }
    Git gitClient;
    if (!isRepositoryAlreadyCheckedOut(project.getId())) {

      if (!project.isVcsOnline()) {
        throw new RuntimeException(
            "The remote git repository has not yet been cloned but the project is set to offline mode. Set the project to online mode and try again.");
      }

      gitClient = cloneRepository(project);
    } else {

      if (!project.isVcsOnline()) {
        // don't pull changes from remote repository, since we are in offline mode
        return getLocalGitRepository(project.getId());
      }

      gitClient = updateLocalRepository(project.getId());
    }
    return gitClient;
  }

  private Git updateLocalRepository(long projectId) throws GitAPIException, IOException {
    return updateRepositoryAdapter.updateInternal(getWorkdir(projectId));
  }

  private Git cloneRepository(Project project) throws GitAPIException {
    if (project.getVcsUrl() == null) {
      throw new IllegalStateException(
          String.format("vcsCoordinates of Project with ID %d are null!", project.getId()));
    }

    CloneRepositoryCommand command =
        new CloneRepositoryCommand(project.getVcsUrl(), getWorkdir(project.getId()).toFile());
    Git git =
        Git.cloneRepository()
            .setURI(command.getRemoteUrl())
            .setDirectory(command.getLocalDir())
            .call();
    git.getRepository().close();
    return git;
  }

  private boolean isRepositoryAlreadyCheckedOut(long projectId) {
    Path projectWorkdir = getWorkdir(projectId);
    return checkRepositoryService.isRepository(projectWorkdir);
  }

  private Path getWorkdir(long projectId) {
    return workdirManager.getLocalGitRoot(projectId);
  }
}
