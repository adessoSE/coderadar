package io.reflectoring.coderadar.core.vcs.domain;

import org.eclipse.jgit.api.Git;

public interface CommitProcessor {

  void processCommit(Git gitClient, RevCommitWithSequenceNumber commit);
}
