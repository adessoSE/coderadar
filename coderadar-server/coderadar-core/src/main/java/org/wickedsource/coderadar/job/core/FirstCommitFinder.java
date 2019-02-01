package org.wickedsource.coderadar.job.core;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;

@Service
public class FirstCommitFinder {

	@Autowired private CommitRepository commitRepository;

	/**
	* Determines if the given commit is the first commit in the project.
	*
	* @param commit the commit in question.
	* @return true if the specified commit is the first in it's project.
	*/
	public boolean isFirstCommitInProject(Commit commit) {
		Date startDate = commit.getProject().getVcsCoordinates().getStartDate();
		if (startDate == null) {
			return false;
		} else {
			Commit firstCommitInDateRange =
					commitRepository.findFirstCommitAfterDate(commit.getProject().getId(), startDate);
			return firstCommitInDateRange != null
					&& firstCommitInDateRange.getId().equals(commit.getId());
		}
	}
}
