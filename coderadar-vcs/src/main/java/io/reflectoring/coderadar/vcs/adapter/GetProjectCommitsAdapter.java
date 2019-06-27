package io.reflectoring.coderadar.vcs.adapter;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.query.domain.DateRange;
import io.reflectoring.coderadar.vcs.port.driven.GetProjectCommitsPort;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
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
public class GetProjectCommitsAdapter implements GetProjectCommitsPort {

  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  @Autowired
  public GetProjectCommitsAdapter(
      CoderadarConfigurationProperties coderadarConfigurationProperties) {
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
  }

  public List<Commit> getCommits(Path repositoryRoot, DateRange range) {
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

      HashMap<ObjectId, Commit> map = new HashMap<>();
      AtomicReference<Boolean> done = new AtomicReference<>(false);
      git.log()
          .call()
          .forEach(
              rc -> {
                // Find the first commit in the given date range and build the tree from it.
                if (!done.get() && isInDateRange(range, rc)) {
                  final RevWalk revWalk = new RevWalk(git.getRepository());

                  Commit commit = new Commit();
                  commit.setName(rc.getName());
                  commit.setAuthor(rc.getAuthorIdent().getName());
                  commit.setComment(rc.getShortMessage());
                  commit.setTimestamp(Date.from(Instant.ofEpochSecond(rc.getCommitTime())));
                  try {
                    commit.setParents(getParents(revWalk, rc, map, range.getEndDate()));
                  } catch (IOException e) {
                    e.printStackTrace();
                  }
                  done.set(true);
                }
              });
      return new ArrayList<>(map.values());
    } catch (Exception e) {
      throw new IllegalStateException(
          String.format("Error accessing git repository at %s", repositoryRoot), e);
    }
  }

  /**
   * Recursively walks through all the parents of a commit and builds the commit tree.
   *
   * @param revWalk RevWalk Object we use to efficiently get the parent commits of a any commit.
   * @param commit The start commit. This should be the newest commit.
   * @param walkedCommits A Map storing the commits we have walked so far, this prevents us from
   *     walking the same commits more than once.
   * @param endDate The date past which no parents are checked.
   * @return A List of parents for the commit.
   * @throws IOException Thrown if for any reason a parent commit cannot be properly parsed.
   */
  private List<Commit> getParents(
      RevWalk revWalk, RevCommit commit, HashMap<ObjectId, Commit> walkedCommits, LocalDate endDate)
      throws IOException {

    List<Commit> parents = new ArrayList<>();

    for (RevCommit rc : commit.getParents()) {

      Commit commitWithParents = walkedCommits.get(rc.getId());
      if (commitWithParents != null) {
        parents.add(commitWithParents);
      } else {
        RevCommit commitWithMetadata = revWalk.parseCommit(rc.getId());
        if (endDate == null
            || !Instant.ofEpochSecond(rc.getCommitTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .isBefore(endDate)) {
          continue;
        }
        commitWithParents = new Commit();
        commitWithParents.setName(commitWithMetadata.getName());
        commitWithParents.setAuthor(commitWithMetadata.getAuthorIdent().getName());
        commitWithParents.setComment(commitWithMetadata.getShortMessage());
        commitWithParents.setTimestamp(Date.from(Instant.ofEpochSecond(rc.getCommitTime())));
        walkedCommits.put(rc.getId(), commitWithParents);
        commitWithParents.setParents(
            getParents(revWalk, commitWithMetadata, walkedCommits, endDate));
        parents.add(commitWithParents);
      }
    }
    return parents;
  }

  /**
   * @param range Date range to test for
   * @param rc RevCommit to check
   * @return True if the commit was made within the date range, false otherwise.
   */
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
