package org.wickedsource.coderadar.vcs.git.walk;

import org.eclipse.jgit.revwalk.RevCommit;

public class RevCommitWithSequenceNumber {

	private final RevCommit commit;

	private final int sequenceNumber;

	public RevCommitWithSequenceNumber(RevCommit commit, int sequenceNumber) {
		this.commit = commit;
		this.sequenceNumber = sequenceNumber;
	}

	public RevCommit getCommit() {
		return commit;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}
}
