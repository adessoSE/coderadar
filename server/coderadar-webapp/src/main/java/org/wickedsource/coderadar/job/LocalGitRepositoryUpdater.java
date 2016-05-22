package org.wickedsource.coderadar.job;

import org.eclipse.jgit.api.Git;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.core.WorkdirManager;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.vcs.git.GitRepositoryChecker;
import org.wickedsource.coderadar.vcs.git.GitRepositoryCloner;
import org.wickedsource.coderadar.vcs.git.GitRepositoryUpdater;

import java.nio.file.Path;

@Service
public class LocalGitRepositoryUpdater {

    private GitRepositoryUpdater gitUpdater;

    private GitRepositoryCloner gitCloner;

    private GitRepositoryChecker gitChecker;

    private WorkdirManager workdirManager;

    @Autowired
    public LocalGitRepositoryUpdater(GitRepositoryUpdater gitUpdater, GitRepositoryCloner gitCloner, GitRepositoryChecker gitChecker, WorkdirManager workdirManager) {
        this.gitUpdater = gitUpdater;
        this.gitCloner = gitCloner;
        this.gitChecker = gitChecker;
        this.workdirManager = workdirManager;
    }

    public Git updateLocalGitRepository(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("parameter project must not be null!");
        }
        Git gitClient;
        if (!isRepositoryAlreadyCheckedOut(project)) {
            gitClient = cloneRepository(project);
        } else {
            gitClient = updateLocalRepository(project);
        }
        return gitClient;
    }

    private Git updateLocalRepository(Project project) {
        return gitUpdater.updateRepository(getWorkdir(project));
    }

    private Git cloneRepository(Project project) {
        if (project.getVcsCoordinates() == null) {
            throw new IllegalStateException(String.format("vcsCoordinates of Project with ID %d are null!", project.getId()));
        }
        return gitCloner.cloneRepository(project.getVcsCoordinates().getUrl().toString(), getWorkdir(project).toFile());
    }

    private boolean isRepositoryAlreadyCheckedOut(Project project) {
        Path projectWorkdir = getWorkdir(project);
        return gitChecker.isRepository(projectWorkdir);
    }

    private Path getWorkdir(Project project) {
        return workdirManager.getLocalGitRoot(project.getId());
    }
}
