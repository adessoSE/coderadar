package org.wickedsource.coderadar.job.scan.commit;

import com.codahale.metrics.Meter;
import java.util.Date;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.vcs.git.walk.CommitProcessor;
import org.wickedsource.coderadar.vcs.git.walk.RevCommitWithSequenceNumber;

/** Takes a GIT commit and stores it in the database. */
class PersistingCommitProcessor implements CommitProcessor {

	private CommitRepository commitRepository;

	private Project project;

	private Meter commitsMeter;

	private int updatedCommits;

	PersistingCommitProcessor(
			CommitRepository commitRepository, Project project, Meter commitsMeter) {
		this.commitRepository = commitRepository;
		this.project = project;
		this.commitsMeter = commitsMeter;
	}

	/**
	* Takes a JGit commit and stores a corresponding {@link Commit} entity in the database.
	*
	* @param gitClient the git repository
	* @param commitWithSequenceNumber a git commit with a number defining its sequence in relation to
	*     the other commits.
	*/
	@Override
	public void processCommit(Git gitClient, RevCommitWithSequenceNumber commitWithSequenceNumber) {
		Commit commit = new Commit();
		RevCommit gitCommit = commitWithSequenceNumber.getCommit();
		commit.setName(gitCommit.getName());
		commit.setAuthor(gitCommit.getAuthorIdent().getName());
		commit.setComment(gitCommit.getShortMessage());
		commit.setProject(project);
		commit.setTimestamp(new Date(gitCommit.getCommitTime() * 1000L));
		commit.setSequenceNumber(commitWithSequenceNumber.getSequenceNumber());
		if (gitCommit.getParentCount() > 0) {
			commit.setFirstParent(gitCommit.getParent(0).getName());
		}
		commitRepository.save(commit);
		updatedCommits++;
		commitsMeter.mark();
	}

	public int getUpdatedCommitsCount() {
		return updatedCommits;
	}
}
