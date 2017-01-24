package org.wickedsource.coderadar.vcs.git.walk;

import org.eclipse.jgit.api.Git;

public interface CommitWalker {

  /**
   * Walks the given Git repository and passes all commits that are walked to a CommitProcessor.
   *
   * @param gitClient the git client pointing at the git repository to be analyzed.
   * @param commitProcessor the processor that processes all commits the walker passes.
   */
  void walk(Git gitClient, CommitProcessor commitProcessor);
}
