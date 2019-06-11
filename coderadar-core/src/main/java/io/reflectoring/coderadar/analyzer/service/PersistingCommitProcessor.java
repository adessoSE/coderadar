package io.reflectoring.coderadar.analyzer.service;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import io.reflectoring.coderadar.vcs.domain.RevCommitWithSequenceNumber;
import io.reflectoring.coderadar.vcs.port.driver.ProcessCommitUseCase;
import java.util.Date;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;

/** Takes a GIT commit and stores it in the database. */
class PersistingCommitProcessor implements ProcessCommitUseCase {

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
    commit.setTimestamp(new Date(gitCommit.getCommitTime() * 1000L));
    commit.setSequenceNumber(commitWithSequenceNumber.getSequenceNumber());
    /* TODO: Get a commit hash?? from the vcs ports
    commit.setParents(gitCommit.getParents());
    */
    saveCommitPort.saveCommit(commit);
    updatedCommits++;
    // commitsMeter.mark();
  }

  public int getUpdatedCommitsCount() {
    return updatedCommits;
  }
}
