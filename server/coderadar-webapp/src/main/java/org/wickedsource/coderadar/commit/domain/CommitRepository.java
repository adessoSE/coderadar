package org.wickedsource.coderadar.commit.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.wickedsource.coderadar.job.core.ProcessingStatus;

import java.util.List;

public interface CommitRepository extends PagingAndSortingRepository<Commit, Long> {

    Commit findTop1ByProjectIdOrderByTimestampDesc(Long projectId);

    List<Commit> findByScannedFalse();

    List<Commit> findByProjectIdAndScannedTrueAndMergedFalseOrderByTimestamp(long projectId);

    int countByProjectIdAndScannedTrueAndMergedFalse(Long id);

    int countByProjectId(Long id);

    @Query("select c from Commit c where c.merged = true and c.analyzed = false and c.id not in (select j.commit.id from AnalyzeCommitJob j where j.processingStatus in (:ignoredProcessingStatus)) and c.project.id in (select s.project.id from AnalyzingStrategy s where s.active=true and s.fromDate < c.timestamp)")
    List<Commit> findCommitsToBeAnalyzed(@Param("ignoredProcessingStatus") List<ProcessingStatus> ignoredProcessingStatus);

    int deleteByProjectId(Long projectId);

    @Query("update Commit c set c.analyzed=false where c.project.id = :projectId")
    @Modifying
    int resetAnalyzedFlagForProjectId(@Param("projectId") Long projectId);

    Commit findByName(String commitName);
}
