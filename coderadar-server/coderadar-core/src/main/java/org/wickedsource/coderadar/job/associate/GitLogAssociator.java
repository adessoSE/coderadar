package org.wickedsource.coderadar.job.associate;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.project.domain.Project;

@Service
public class GitLogAssociator {

	private Logger logger = LoggerFactory.getLogger(GitLogAssociator.class);

	private CommitRepository commitRepository;

	private CommitToFileAssociator commitToFileAssociator;

	@Autowired
	public GitLogAssociator(
			CommitRepository commitRepository, CommitToFileAssociator commitToFileAssociator) {
		this.commitRepository = commitRepository;
		this.commitToFileAssociator = commitToFileAssociator;
	}

	/**
	* Goes through all files in all commits of the specified project and associates them with other
	* files to support git RENAMEs.
	*
	* @param project the project whose files to associate.
	*/
	public void associate(Project project) {
		List<Commit> commitsToMerge =
				commitRepository.findByProjectIdAndScannedTrueAndMergedFalseOrderBySequenceNumber(
						project.getId());
		for (Commit commit : commitsToMerge) {
			commitToFileAssociator.associateFilesOfCommit(commit);
		}
		logger.info("processed {} commits for project {}", commitsToMerge.size(), project);
	}
}
