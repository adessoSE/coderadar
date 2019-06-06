package io.reflectoring.coderadar.vcs.service;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.vcs.port.driver.CloneRepositoryCommand;
import java.io.IOException;
import java.nio.file.Path;
import org.eclipse.jgit.api.Git;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocalGitRepositoryManager {

  private final UpdateRepositoryService updateRepositoryService;

  private final CloneRepositoryService cloneRepositoryService;

  private final CheckRepositoryService checkRepositoryService;

  private final WorkdirManager workdirManager;

  @Autowired
  public LocalGitRepositoryManager(
      UpdateRepositoryService updateRepositoryService,
      CloneRepositoryService cloneRepositoryService,
      CheckRepositoryService checkRepositoryService,
      WorkdirManager workdirManager) {
    this.updateRepositoryService = updateRepositoryService;
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

  public Git updateLocalGitRepository(Project project) {
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

  private Git updateLocalRepository(long projectId) {
    return updateRepositoryService.updateRepository(getWorkdir(projectId));
  }

  private Git cloneRepository(Project project) {
    if (project.getVcsUrl() == null) {
      throw new IllegalStateException(
          String.format("vcsCoordinates of Project with ID %d are null!", project.getId()));
    }

    CloneRepositoryCommand command =
        new CloneRepositoryCommand(project.getVcsUrl(), getWorkdir(project.getId()).toFile());
    return cloneRepositoryService.cloneRepository(command);
  }

  private boolean isRepositoryAlreadyCheckedOut(long projectId) {
    Path projectWorkdir = getWorkdir(projectId);
    return checkRepositoryService.isRepository(projectWorkdir);
  }

  private Path getWorkdir(long projectId) {
    return workdirManager.getLocalGitRoot(projectId);
  }
}
