package org.wickedsource.coderadar.commit.domain;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.wickedsource.coderadar.job.core.ProcessingStatus;

public interface CommitRepository extends PagingAndSortingRepository<Commit, Long> {

  Commit findTop1ByProjectIdOrderBySequenceNumberDesc(Long projectId);

  List<Commit> findByScannedFalse();

  List<Commit> findByProjectIdAndScannedTrueAndMergedFalseOrderBySequenceNumber(long projectId);

  int countByProjectIdAndScannedTrueAndMergedFalse(Long projectId);

  int countByProjectId(Long projectId);

  @Query(
      "select c from Commit c where c.merged = true and c.analyzed = false and c.id not in (select j.commit.id from AnalyzeCommitJob j where j.processingStatus in (:ignoredProcessingStatus)) and c.project.id in (select aj.project.id from AnalyzingJob aj where aj.active=true and aj.fromDate < c.timestamp)")
  List<Commit> findCommitsToBeAnalyzed(
      @Param("ignoredProcessingStatus") List<ProcessingStatus> ignoredProcessingStatus);

  int deleteByProjectId(Long projectId);

  @Query("update Commit c set c.analyzed=false where c.project.id = :projectId")
  @Modifying
  int resetAnalyzedFlagForProjectId(@Param("projectId") Long projectId);

  Commit findByName(String commitName);

  /**
   * Returns the last Commit of each day in the given date range.
   *
   * @param projectId ID of the project whose commits to consider.
   * @param startDate start date of date range in which to consider commits.
   * @param endDate end date of date range in which to consider commits.
   * @return List of commits, each being the last commit of a day.
   */
  @Query(name = "Commit.findLastForEachDay")
  List<Commit> findLastForEachDay(
      @Param("projectId") Long projectId,
      @Param("startDate") Date startDate,
      @Param("endDate") Date endDate);

  /**
   * Returns the last Commit of each week in the given date range.
   *
   * @param projectId ID of the project whose commits to consider.
   * @param startDate start date of date range in which to consider commits.
   * @param endDate end date of date range in which to consider commits.
   * @return List of commits, each being the last commit of a week.
   */
  @Query(name = "Commit.findLastForEachWeek")
  List<Commit> findLastForEachWeek(
      @Param("projectId") Long projectId,
      @Param("startDate") Date startDate,
      @Param("endDate") Date endDate);

  @Query(
      "select c from Commit c where c.project.id = :projectId and c.timestamp = (select min(c2.timestamp) from Commit c2 where c2.timestamp >= :date)")
  Commit findFirstCommitAfterDate(@Param("projectId") Long projectId, @Param("date") Date date);

  Page<Commit> findByProjectId(Long projectId, Pageable pageable);
}
