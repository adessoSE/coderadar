package org.wickedsource.coderadar.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.CoderadarConfiguration;

import java.nio.file.Path;

/**
 * Centralizes the access to local working directories needed for some tasks.
 */
@Service
public class WorkdirManager {

    private CoderadarConfiguration config;

    @Autowired
    public WorkdirManager(CoderadarConfiguration config) {
        this.config = config;
    }

    /**
     * Returns the path to the local GIT repository for the specified project. Creates the folder
     * if it does not exist.
     *
     * @param projectId ID of the project for which to return the path.
     * @return path to the local GIT repository of the specified project.
     */
    public Path getLocalGitRoot(Long projectId) {
        Path workdir = config.getWorkdir().resolve("projects/" + projectId);
        createDirIfNecessary(workdir);
        return workdir;
    }

    private void createDirIfNecessary(Path workdir) {
        if (!workdir.toFile().exists()) {
            workdir.toFile().mkdirs();
        }
    }

    private String timestamp() {
        return String.valueOf(System.currentTimeMillis());
    }
}
