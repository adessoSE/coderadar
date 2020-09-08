package io.reflectoring.coderadar.vcs;

import io.reflectoring.coderadar.query.domain.DateRange;
import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.revwalk.RevCommit;

public class RevCommitHelper {

  private RevCommitHelper() {}

  public static List<RevCommit> getRevCommits(String repositoryRoot) {
    try (Git git = Git.open(new File(repositoryRoot))) {
      // Most projects have >100 commits.
      List<RevCommit> revCommits = new ArrayList<>(100);
      git.log().all().call().forEach(revCommits::add);
      Collections.reverse(revCommits);
      return revCommits;
    } catch (NoHeadException e) {
      return new ArrayList<>(0);
    } catch (Exception e) {
      throw new IllegalStateException(
          String.format("Error accessing git repository at %s", repositoryRoot), e);
    }
  }

  public static List<RevCommit> getRevCommitsInDateRange(String repositoryRoot, DateRange range) {
    return getRevCommits(repositoryRoot).stream()
        .filter(revCommit -> isInDateRange(range, revCommit))
        .collect(Collectors.toList());
  }

  /**
   * @param range Date range to test for
   * @param rc RevCommit to check
   * @return True if the commit was made within the date range, false otherwise.
   */
  private static boolean isInDateRange(DateRange range, RevCommit rc) {
    LocalDate commitTime =
        Instant.ofEpochSecond(rc.getCommitTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    return range.containsDate(commitTime);
  }
}
