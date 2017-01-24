package org.wickedsource.coderadar.vcs.git.walk;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;

public interface CommitProcessor {

  void processCommit(Git gitClient, RevCommit commit);
}
