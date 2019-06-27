package io.reflectoring.coderadar.vcs.service;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

/** Centralizes the access to local working directories needed for some tasks. */
@Service
public class WorkdirManager {

  private CoderadarConfigurationProperties config;
  private final GetProjectPort getProjectPort;

  @Autowired
  public WorkdirManager(CoderadarConfigurationProperties config, GetProjectPort getProjectPort) {
    this.config = config;
    this.getProjectPort = getProjectPort;
  }

  /**
   * Returns the path to the local GIT repository for the specified project. Creates the folder if
   * it does not exist.
   *
   * @param projectId ID of the project whose working directory to find.
   * @return path to the local GIT repository of the specified project.
   */
  public Path getLocalGitRoot(Long projectId) {
    Project project = getProjectPort.get(projectId);
    Path workdir = config.getWorkdir().resolve("projects/" + project.getWorkdirName());
    createDirIfNecessary(workdir);
    return workdir;
  }

  private void createDirIfNecessary(Path workdir) {
    if (!workdir.toFile().exists()) {
      workdir.toFile().mkdirs();
    }
  }
}
