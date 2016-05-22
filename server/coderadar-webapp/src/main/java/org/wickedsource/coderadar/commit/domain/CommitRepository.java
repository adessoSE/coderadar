package org.wickedsource.coderadar.commit.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommitRepository extends CrudRepository<Commit, Long> {

    Commit findTop1ByProjectIdOrderByTimestampDesc(Long projectId);

    List<Commit> findByProjectIdAndAnalyzedFalseOrderByTimestamp(Long projectId);
}
