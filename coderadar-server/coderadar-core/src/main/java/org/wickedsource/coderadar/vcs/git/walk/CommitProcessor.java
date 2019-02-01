package org.wickedsource.coderadar.vcs.git.walk;

import org.eclipse.jgit.api.Git;

@FunctionalInterface
public interface CommitProcessor {

	void processCommit(Git gitClient, RevCommitWithSequenceNumber commit);
}
