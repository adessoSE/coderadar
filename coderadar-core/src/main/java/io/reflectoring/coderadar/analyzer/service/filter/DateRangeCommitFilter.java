package io.reflectoring.coderadar.analyzer.service.filter;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.vcs.domain.CommitFilter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * A Commit filter that only includes a commit if it was performed within a specified date range.
 */
public class DateRangeCommitFilter implements CommitFilter {

  private final LocalDate startDate;

  private final LocalDate endDate;

  /**
   * Constructor.
   *
   * @param startDate the start date (inclusive) of the date range a commit must be in to be
   *     processed. Leave empty to start at the first commit.
   * @param endDate the end date (inclusive) of the date range a commit must be in to be processed.
   *     Leave empty to include all commits to the most current one.
   */
  public DateRangeCommitFilter(LocalDate startDate, LocalDate endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

  @Override
  public boolean shouldBeProcessed(Commit commit) {
    LocalDate commitDate =
        Instant.ofEpochSecond(commit.getTimestamp().getTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    if (this.startDate != null && commitDate.isBefore(this.startDate)) {
      return false;
    }
    return this.endDate == null || !commitDate.isAfter(this.endDate);
  }
}
