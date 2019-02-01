package org.wickedsource.coderadar.vcs.git.walk.filter;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.wickedsource.coderadar.vcs.git.GitCommitFinder;

/** A filter that walks only those commits that are newer than a specified commit. */
public class LastKnownCommitFilter implements CommitWalkerFilter {

	private final GitCommitFinder commitFinder;

	private final RevCommit lastKnownCommit;

	/**
	* Construcotr.
	*
	* @param gitClient the git client pointing to the local git repository.
	* @param lastKnownCommitName the name of the last known commit. Only commits newer than this
	*     commits are walked. If the name is null, all commits will be walked. If a commit with the
	*     specified name does not exist, an {@link IllegalArgumentException} is thrown.
	*/
	public LastKnownCommitFilter(Git gitClient, String lastKnownCommitName) {
		this.commitFinder = new GitCommitFinder();
		if (lastKnownCommitName != null) {
			lastKnownCommit = commitFinder.findCommit(gitClient, lastKnownCommitName);
			if (lastKnownCommit == null) {
				throw new IllegalArgumentException(
						String.format("Last known commit with name %s does not exist!", lastKnownCommitName));
			}
		} else {
			lastKnownCommit = null;
		}
	}

	@Override
	public boolean shouldBeProcessed(RevCommit commit) {
		return this.lastKnownCommit == null || isNewerThan(commit, lastKnownCommit);
	}

	private boolean isNewerThan(RevCommit commit, RevCommit referenceCommit) {
		return commit.getCommitTime() > referenceCommit.getCommitTime();
	}
}
