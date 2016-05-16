package org.wickedsource.coderadar.job.execute.scan;

import org.eclipse.jgit.api.Git;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.CoderadarConfiguration;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.domain.ProjectRepository;
import org.wickedsource.coderadar.vcs.git.GitRepositoryChecker;
import org.wickedsource.coderadar.vcs.git.GitRepositoryCloner;
import org.wickedsource.coderadar.vcs.git.GitRepositoryUpdater;
import org.wickedsource.coderadar.vcs.git.walk.AllCommitsWalker;

import java.nio.file.Path;

@Service
public class VcsScanner {

    private Logger logger = LoggerFactory.getLogger(VcsScanner.class);

    private CoderadarConfiguration config;

    private ProjectRepository projectRepository;

    private CommitRepository commitRepository;

    private GitRepositoryCloner gitCloner;

    private GitRepositoryChecker gitChecker;

    private GitRepositoryUpdater gitUpdater;


    @Autowired
    public VcsScanner(CoderadarConfiguration config, ProjectRepository projectRepository, CommitRepository commitRepository, GitRepositoryCloner gitCloner, GitRepositoryChecker gitChecker, GitRepositoryUpdater gitUpdater) {
        this.config = config;
        this.projectRepository = projectRepository;
        this.commitRepository = commitRepository;
        this.gitCloner = gitCloner;
        this.gitChecker = gitChecker;
        this.gitUpdater = gitUpdater;
    }

    public void scanVcs(Long projectId) {
        if (config.isSlave()) {
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
        }
    }

    private void scanLocalRepository(Project project, Git gitClient) {
        Commit latestCommit = commitRepository.findTop1ByProjectIdOrderByTimestampDesc(project.getId());
        AllCommitsWalker walker = new AllCommitsWalker();
        if (latestCommit != null) {
            walker.stopAtCommit(latestCommit.getName());
        }
        DatabaseUpdatingCommitProcessor commitProcessor = new DatabaseUpdatingCommitProcessor(commitRepository, project);
        walker.walk(gitClient, commitProcessor);
        logger.info("scan result for project {}: {} new commits", project.getId(), commitProcessor.getUpdatedCommitsCount());
    }

    private Git updateLocalRepository(Project project) {
        return gitUpdater.updateRepository(getProjectWorkdir(project));
    }

    private Git cloneRepository(Project project) {
        if (project.getVcsCoordinates() == null) {
            throw new IllegalStateException(String.format("vcsCoordinates of Project with ID %d are null!", project.getId()));
        }
        return gitCloner.cloneRepository(project.getVcsCoordinates().getUrl().toString(), getProjectWorkdir(project).toFile());
    }

    private boolean isRepositoryAlreadyCheckedOut(Project project) {
        Path projectWorkdir = getProjectWorkdir(project);
        return gitChecker.isRepository(projectWorkdir);
    }

    private Path getProjectWorkdir(Project project) {
        Path globalWorkdir = config.getWorkdir();
        Path projectWorkdir = globalWorkdir.resolve("project-" + project.getId());
        if (!projectWorkdir.toFile().exists()) {
            projectWorkdir.toFile().mkdirs();
        }
        return projectWorkdir;
    }

}
