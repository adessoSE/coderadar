package io.reflectoring.coderadar.vcs.port.driver;

import io.reflectoring.coderadar.vcs.domain.RevCommitWithSequenceNumber;
import org.eclipse.jgit.api.Git;

public interface ProcessCommitUseCase {

  void processCommit(Git gitClient, RevCommitWithSequenceNumber commit);
}
