package org.wickedsource.coderadar.commit.domain;

import org.springframework.data.repository.CrudRepository;

public interface CommitRepository extends CrudRepository<Commit, Long> {

    Commit findTop1ByProjectIdOrderByTimestampDesc(Long projectId);
}
