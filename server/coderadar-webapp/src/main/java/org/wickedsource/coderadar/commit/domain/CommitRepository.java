package org.wickedsource.coderadar.commit.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommitRepository extends CrudRepository<Commit, Long> {

    Commit findTop1ByProjectIdOrderByTimestampDesc(Long projectId);

    List<Commit> findByScannedFalse();

    Commit findTop1ByProjectIdAndScannedFalseOrderByTimestamp(Long projectId);

    List<Commit> findByProjectIdAndScannedTrueAndMergedFalseOrderByTimestamp(long projectId);

    int countByProjectIdAndScannedTrueAndMergedFalse(Long id);

    int countByProjectId(Long id);

    Commit findByName(String parentCommitName);
}
