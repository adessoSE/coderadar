package io.reflectoring.coderadar.vcs.port.driver.walk.findCommit;

import org.eclipse.jgit.revwalk.RevCommit;

public interface FindGitCommitUseCase {
  RevCommit findCommit(FindGitCommitCommand command);
}
