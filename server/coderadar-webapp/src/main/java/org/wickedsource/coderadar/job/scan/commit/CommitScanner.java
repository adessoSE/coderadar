package org.wickedsource.coderadar.job.scan.commit;

import org.eclipse.jgit.api.Git;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.job.LocalGitRepositoryUpdater;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.vcs.git.GitCommitFinder;
import org.wickedsource.coderadar.vcs.git.walk.AllCommitsWalker;

import java.io.File;

@Service
public class CommitScanner {

    private Logger logger = LoggerFactory.getLogger(CommitScanner.class);

    private CommitRepository commitRepository;

    private LocalGitRepositoryUpdater updater;

    private GitCommitFinder commitFinder;

    @Autowired
    public CommitScanner(CommitRepository commitRepository, LocalGitRepositoryUpdater updater, GitCommitFinder commitFinder) {
        this.commitRepository = commitRepository;
        this.updater = updater;
        this.commitFinder = commitFinder;
    }

    /**
     * Scans the local GIT repository of the specified project and stores metadata about each commit in the database.
     * If the local GIT repository does not exist, the remote repository of the project is cloned into a
     * local repository first. If it exists, it will be updated to the state of the remote repository.
     *
     * @param project he project whose repository to scan.
     * @return File object of the local GIT repository.
     */
    public File scan(Project project) {
        Git gitClient = updater.updateLocalGitRepository(project);
        scanLocalRepository(project, gitClient);
        return gitClient.getRepository().getDirectory();
    }

    private void scanLocalRepository(Project project, Git gitClient) {
        Commit lastKnownCommit = commitRepository.findTop1ByProjectIdOrderByTimestampDesc(project.getId());
        AllCommitsWalker walker = new AllCommitsWalker();
        if (lastKnownCommit != null) {
            walker.stopAtCommit(lastKnownCommit.getName());
        }
        PersistingCommitProcessor commitProcessor = new PersistingCommitProcessor(commitRepository, project, commitFinder);
        walker.walk(gitClient, commitProcessor);
        gitClient.getRepository().close();
        logger.info("scanned {} new commits for project {}", commitProcessor.getUpdatedCommitsCount(), project);
    }


}
