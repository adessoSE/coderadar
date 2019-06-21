package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import io.reflectoring.coderadar.query.domain.DateRange;
import io.reflectoring.coderadar.vcs.port.driven.SaveProjectCommitsPort;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveProjectCommitsAdapter implements SaveProjectCommitsPort {

  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  private final SaveCommitPort saveCommitPort;

  @Autowired
  public SaveProjectCommitsAdapter(
      CoderadarConfigurationProperties coderadarConfigurationProperties,
      ProcessRepositoryAdapter processRepositoryAdapter,
      SaveCommitPort saveCommitPort) {
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
    this.saveCommitPort = saveCommitPort;
  }

  public void saveCommits(Path repositoryRoot, DateRange range) {
    Git git;
    try {
      Repository repository;
      Path actualPath =
          Paths.get(coderadarConfigurationProperties.getWorkdir() + "/projects/" + repositoryRoot);

      FileRepositoryBuilder builder = new FileRepositoryBuilder();
      repository =
          builder
              .setWorkTree(actualPath.toFile())
              .setGitDir(new File(actualPath + "/.git"))
              .build();
      git = new Git(repository);

      AtomicReference<Boolean> done = new AtomicReference<>(false);
      git.log()
          .call()
          .forEach(
              rc -> {
                if (!done.get() && isInDateRange(range, rc)) {
                  final RevWalk revWalk = new RevWalk(git.getRepository());

                  Commit commit = new Commit();
                  commit.setName(rc.getName());
                  commit.setAuthor(rc.getAuthorIdent().getName());
                  commit.setComment(rc.getShortMessage());
                  commit.setTimestamp(new Date(rc.getCommitTime()));
                  try {
                    commit.setParents(getParents(revWalk, rc, new HashMap<>(), range));
                  } catch (IOException e) {
                    e.printStackTrace();
                  }
                  commit.getParents().forEach(commit1 -> commit1.getParents().clear());
                  saveCommitPort.saveCommit(commit);

                  done.set(true);
                }
              });
    } catch (Exception e) {
      throw new IllegalStateException(
          String.format("Error accessing git repository at %s", repositoryRoot), e);
    }
  }

  private List<Commit> getParents(
      RevWalk revWalk, RevCommit commit, HashMap<ObjectId, Commit> walkedCommits, DateRange range)
      throws IOException {

    List<Commit> parents = new ArrayList<>();

    for (RevCommit rc : commit.getParents()) {

      Commit commitWithParents = walkedCommits.get(rc.getId());
      if (commitWithParents != null) {
        parents.add(commitWithParents);
      } else {
        RevCommit commitWithMetadata = revWalk.parseCommit(rc.getId());
        if (!isInDateRange(range, commitWithMetadata)) {
          continue;
        }
        commitWithParents = new Commit();
        commitWithParents.setName(commitWithMetadata.getName());
        commitWithParents.setAuthor(commitWithMetadata.getAuthorIdent().getName());
        commitWithParents.setComment(commitWithMetadata.getShortMessage());
        commitWithParents.setTimestamp(new Date(commitWithMetadata.getCommitTime()));
        walkedCommits.put(rc.getId(), commitWithParents);
        commitWithParents.setParents(getParents(revWalk, commitWithMetadata, walkedCommits, range));
        parents.add(commitWithParents);
      }
    }
    parents.forEach(
        commit1 -> {
          commit1.getParents().forEach(commit2 -> commit2.getParents().clear());
          saveCommitPort.saveCommit(commit1);
        });
    return parents;
  }

  private boolean isInDateRange(DateRange range, RevCommit rc) {
    return Instant.ofEpochSecond(rc.getCommitTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .isBefore(range.getEndDate())
        && Instant.ofEpochSecond(rc.getCommitTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .isAfter(range.getStartDate());
  }
}
