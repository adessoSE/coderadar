package org.wickedsource.coderadar.job.scan;

import org.eclipse.jgit.api.Git;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.core.WorkdirManager;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;
import org.wickedsource.coderadar.vcs.git.GitRepositoryChecker;
import org.wickedsource.coderadar.vcs.git.GitRepositoryCloner;
import org.wickedsource.coderadar.vcs.git.GitRepositoryUpdater;
import org.wickedsource.coderadar.vcs.git.walk.AllCommitsWalker;

import java.io.File;
import java.nio.file.Path;

@Service
public class VcsRepositoryScanner {

    private Logger logger = LoggerFactory.getLogger(VcsRepositoryScanner.class);

    private ProjectRepository projectRepository;

    private CommitRepository commitRepository;

    private GitRepositoryCloner gitCloner;

    private GitRepositoryChecker gitChecker;

    private GitRepositoryUpdater gitUpdater;

    private WorkdirManager workdirManager;


    @Autowired
    public VcsRepositoryScanner(ProjectRepository projectRepository, CommitRepository commitRepository, GitRepositoryCloner gitCloner, GitRepositoryChecker gitChecker, GitRepositoryUpdater gitUpdater, WorkdirManager workdirManager) {
        this.projectRepository = projectRepository;
        this.commitRepository = commitRepository;
        this.gitCloner = gitCloner;
        this.gitChecker = gitChecker;
        this.gitUpdater = gitUpdater;
        this.workdirManager = workdirManager;
    }

    /**
     * Scans the local GIT repository of the specified project and stores metadata about each commit in the database.
     * If the local GIT repository does not exist, the remote repository of the project is cloned into a
     * local repository first. If it exists, it will be updated to the state of the remote repository.
     *
     * @param projectId ID of the project whose repository to scan.
     * @return File object of the local GIT repository.
     */
    public File scan(Long projectId) {
        Project project = projectRepository.findOne(projectId);
        if (project == null) {
            throw new IllegalArgumentException(String.format("Project with ID %d does not exist!", projectId));
        }
        Git gitClient;
        if (!isRepositoryAlreadyCheckedOut(project)) {
            gitClient = cloneRepository(project);
        } else {
            gitClient = updateLocalRepository(project);
        }
        scanLocalRepository(project, gitClient);
        return gitClient.getRepository().getDirectory();
    }

    private void scanLocalRepository(Project project, Git gitClient) {
        Commit lastKnownCommit = commitRepository.findTop1ByProjectIdOrderByTimestampDesc(project.getId());
        AllCommitsWalker walker = new AllCommitsWalker();
        if (lastKnownCommit != null) {
            walker.stopAtCommit(lastKnownCommit.getName());
        }
        DatabaseUpdatingCommitProcessor commitProcessor = new DatabaseUpdatingCommitProcessor(commitRepository, project);
        walker.walk(gitClient, commitProcessor);
        gitClient.getRepository().close();
        logger.info("scanned {} new commits for project {}", commitProcessor.getUpdatedCommitsCount(), project);
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
