package org.wickedsource.coderadar.job;

import java.io.IOException;
import java.nio.file.Path;
import org.eclipse.jgit.api.Git;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.core.WorkdirManager;
import org.wickedsource.coderadar.core.rest.validation.UserException;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.vcs.git.GitRepositoryChecker;
import org.wickedsource.coderadar.vcs.git.GitRepositoryCloner;
import org.wickedsource.coderadar.vcs.git.GitRepositoryUpdater;

@Service
public class LocalGitRepositoryManager {

	private GitRepositoryUpdater gitUpdater;

	private GitRepositoryCloner gitCloner;

	private GitRepositoryChecker gitChecker;

	private WorkdirManager workdirManager;

	@Autowired
	public LocalGitRepositoryManager(
			GitRepositoryUpdater gitUpdater,
			GitRepositoryCloner gitCloner,
			GitRepositoryChecker gitChecker,
			WorkdirManager workdirManager) {
		this.gitUpdater = gitUpdater;
		this.gitCloner = gitCloner;
		this.gitChecker = gitChecker;
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

			if (!project.getVcsCoordinates().isOnline()) {
				throw new UserException(
						"The remote git repository has not yet been cloned but the project is set to offline mode. Set the project to online mode and try again.");
			}

			gitClient = cloneRepository(project);
		} else {

			if (!project.getVcsCoordinates().isOnline()) {
				// don't pull changes from remote repository, since we are in offline mode
				return getLocalGitRepository(project.getId());
			}

			gitClient = updateLocalRepository(project.getId());
		}
		return gitClient;
	}

	private Git updateLocalRepository(long projectId) {
		return gitUpdater.updateRepository(getWorkdir(projectId));
	}

	private Git cloneRepository(Project project) {
		if (project.getVcsCoordinates() == null) {
			throw new IllegalStateException(
					String.format("vcsCoordinates of Project with ID %d are null!", project.getId()));
		}
		return gitCloner.cloneRepository(
				project.getVcsCoordinates().getUrl().toString(), getWorkdir(project.getId()).toFile());
	}

	private boolean isRepositoryAlreadyCheckedOut(long projectId) {
		Path projectWorkdir = getWorkdir(projectId);
		return gitChecker.isRepository(projectWorkdir);
	}

	private Path getWorkdir(long projectId) {
		return workdirManager.getLocalGitRoot(projectId);
	}
}
