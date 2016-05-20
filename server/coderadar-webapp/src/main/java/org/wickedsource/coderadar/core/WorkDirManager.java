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

    public Path getWorkdirForProject(Long projectId) {
        Path workdir = config.getWorkdir().resolve("projects/" + projectId);
        createDirIfNecessary(workdir);
        return workdir;
    }

    public Path getWorkdirForVcsScan(Long projectId) {
        Path workdir = getWorkdirForProject(projectId).resolve("scans/" + timestamp());
        createDirIfNecessary(workdir);
        return workdir;
    }

    public Path getWorkdirForCommitSweep(Long projectId) {
        Path workdir = getWorkdirForProject(projectId).resolve("sweeps/" + timestamp());
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
