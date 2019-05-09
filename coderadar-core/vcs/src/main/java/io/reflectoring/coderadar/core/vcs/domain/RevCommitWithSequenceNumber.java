package io.reflectoring.coderadar.core.vcs.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.eclipse.jgit.revwalk.RevCommit;

@AllArgsConstructor
@Getter
public class RevCommitWithSequenceNumber {

  private final RevCommit commit;

  private final int sequenceNumber;
}
