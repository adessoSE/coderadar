package io.reflectoring.coderadar.vcs;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import org.eclipse.jgit.revwalk.RevCommit;

import java.util.Date;

/** Maps a RevCommit object to a VcsCommit object. */
public class RevCommitMapper {

  public static Commit map(RevCommit revCommit) {
    Commit commit = new Commit();
    commit.setName(revCommit.getName());
    commit.setAuthor(revCommit.getAuthorIdent().getName());
    commit.setComment(revCommit.getShortMessage());
    commit.setSequenceNumber(0);
    commit.setTimestamp(new Date(revCommit.getCommitTime()));
    return commit;
  }
}
