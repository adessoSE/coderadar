package io.reflectoring.coderadar.vcs;

import io.reflectoring.coderadar.vcs.domain.VcsCommit;
import org.eclipse.jgit.revwalk.RevCommit;

/** Maps a RevCommit object to a VcsCommit object. */
public class RevCommitMapper {

  public static VcsCommit map(RevCommit revCommit) {
    return new VcsCommit(
        revCommit.getCommitTime(),
        revCommit.getName(),
        revCommit.getAuthorIdent().getName(),
        revCommit.getShortMessage(),
        0);
  }
}
