package org.wickedsource.coderadar.vcs.git.walk.filter;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.wickedsource.coderadar.vcs.git.walk.CommitProcessor;
import org.wickedsource.coderadar.vcs.git.walk.RevCommitWithSequenceNumber;

@FunctionalInterface
public interface CommitWalkerFilter {

	/**
	* Returns true if the specified commit should be included in the CommitWalk and false if not.
	*
	* @param commit the commit to test.
	* @return true if the commit is included in the walk (i.e. {@link
	*     CommitProcessor#processCommit(Git, RevCommitWithSequenceNumber)} should be called for the
	*     commit), false if not.
	*/
	boolean shouldBeProcessed(RevCommit commit);
}
