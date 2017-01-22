package org.wickedsource.coderadar.core;

import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.core.configuration.CoderadarConfiguration;

/** Centralizes the access to local working directories needed for some tasks. */
@Service
public class WorkdirManager {

  private CoderadarConfiguration config;

  @Autowired
  public WorkdirManager(CoderadarConfiguration config) {
    this.config = config;
  }

  /**
   * Returns the path to the local GIT repository for the specified project. Creates the folder if
   * it does not exist.
   *
   * @param projectName name of the project
   * @return path to the local GIT repository of the specified project.
   */
  public Path getLocalGitRoot(String projectName) {
    Path workdir = config.getWorkdir().resolve("projects/" + projectName);
    createDirIfNecessary(workdir);
    return workdir;
  }

  private void createDirIfNecessary(Path workdir) {
    if (!workdir.toFile().exists()) {
      workdir.toFile().mkdirs();
    }
  }
}
