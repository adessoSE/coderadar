package org.wickedsource.coderadar.job.scan.commit;

import static com.codahale.metrics.MetricRegistry.name;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import java.io.File;
import java.time.LocalDate;
import org.eclipse.jgit.api.Git;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.job.LocalGitRepositoryManager;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.vcs.git.walk.CommitWalker;
import org.wickedsource.coderadar.vcs.git.walk.filter.DateRangeCommitFilter;
import org.wickedsource.coderadar.vcs.git.walk.filter.LastKnownCommitFilter;

@Service
public class CommitMetadataScanner {

	private Logger logger = LoggerFactory.getLogger(CommitMetadataScanner.class);

	private CommitRepository commitRepository;

	private LocalGitRepositoryManager gitRepoManager;

	private Meter commitsMeter;

	@Autowired
	public CommitMetadataScanner(
			CommitRepository commitRepository,
			LocalGitRepositoryManager gitRepoManager,
			MetricRegistry metricRegistry) {
		this.commitRepository = commitRepository;
		this.gitRepoManager = gitRepoManager;
		this.commitsMeter = metricRegistry.meter(name(CommitMetadataScanner.class, "commits"));
	}

	/**
	* Scans the local GIT repository of the specified project and stores metadata about each commit
	* in the database. If the local GIT repository does not exist, the remote repository of the
	* project is cloned into a local repository first. If it exists, it will be updated to the state
	* of the remote repository before scanning.
	*
	* @param project he project whose repository to scan.
	* @return File object of the local GIT repository.
	*/
	public File scan(Project project) {
		Git gitClient = gitRepoManager.updateLocalGitRepository(project);
		scanLocalRepository(project, gitClient);
		return gitClient.getRepository().getDirectory();
	}

	private void scanLocalRepository(Project project, Git gitClient) {
		Commit lastKnownCommit =
				commitRepository.findTop1ByProjectIdOrderBySequenceNumberDesc(project.getId());
		CommitWalker walker = new CommitWalker();

		// start at the next unknown commit
		if (lastKnownCommit != null) {
			LastKnownCommitFilter filter =
					new LastKnownCommitFilter(gitClient, lastKnownCommit.getName());
			walker.addFilter(filter);
		}

		// only include commits within the project's specified date range
		if (project.getVcsCoordinates().getStartDate() != null
				|| project.getVcsCoordinates().getEndDate() != null) {
			LocalDate startDate =
					Jsr310Converters.DateToLocalDateConverter.INSTANCE.convert(
							project.getVcsCoordinates().getStartDate());
			LocalDate endDate =
					Jsr310Converters.DateToLocalDateConverter.INSTANCE.convert(
							project.getVcsCoordinates().getEndDate());
			DateRangeCommitFilter filter = new DateRangeCommitFilter(startDate, endDate);
			walker.addFilter(filter);
		}

		PersistingCommitProcessor commitProcessor =
				new PersistingCommitProcessor(commitRepository, project, commitsMeter);
		walker.walk(gitClient, commitProcessor);
		gitClient.getRepository().close();
		logger.info(
				"scanned {} new commits for project {}", commitProcessor.getUpdatedCommitsCount(), project);
	}
}
