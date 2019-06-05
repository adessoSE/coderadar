package io.reflectoring.coderadar.core.analyzer.service;

import io.reflectoring.coderadar.core.projectadministration.domain.Commit;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.analyzer.SaveCommitPort;
import io.reflectoring.coderadar.core.vcs.domain.CommitProcessor;
import io.reflectoring.coderadar.core.vcs.domain.RevCommitWithSequenceNumber;
import java.util.Date;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;

/** Takes a GIT commit and stores it in the database. */
class PersistingCommitProcessor implements CommitProcessor {

  private SaveCommitPort saveCommitPort;

  private Project project;

  // private Meter commitsMeter;

  private int updatedCommits;

  PersistingCommitProcessor(SaveCommitPort saveCommitPort, Project project) {
    this.saveCommitPort = saveCommitPort;
    this.project = project;
  }

  /**
   * Takes a JGit commit and stores a corresponding {@link Commit} entity in the database.
   *
   * @param gitClient the git repository
   * @param commitWithSequenceNumber a git commit with a number defining its sequence in relation to
   *     the other commits.
   */
  @Override
  public void processCommit(Git gitClient, RevCommitWithSequenceNumber commitWithSequenceNumber) {
    Commit commit = new Commit();
    RevCommit gitCommit = commitWithSequenceNumber.getCommit();
    commit.setName(gitCommit.getName());
    commit.setAuthor(gitCommit.getAuthorIdent().getName());
    commit.setComment(gitCommit.getShortMessage());
    commit.setProject(project);
    commit.setTimestamp(new Date(gitCommit.getCommitTime() * 1000L));
    commit.setSequenceNumber(commitWithSequenceNumber.getSequenceNumber());
    if (gitCommit.getParentCount() > 0) {
      commit.setFirstParent(gitCommit.getParent(0).getName());
    }
    project.getCommits().add(commit);
    saveCommitPort.saveCommit(commit);
    updatedCommits++;
    // commitsMeter.mark();
  }

  public int getUpdatedCommitsCount() {
    return updatedCommits;
  }
}
