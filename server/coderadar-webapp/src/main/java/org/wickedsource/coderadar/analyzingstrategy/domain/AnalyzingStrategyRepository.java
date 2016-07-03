package org.wickedsource.coderadar.analyzingstrategy.domain;

import org.springframework.data.repository.CrudRepository;

public interface AnalyzingStrategyRepository extends CrudRepository<AnalyzingStrategy, Long> {

    AnalyzingStrategy findByProjectId(long projectId);

    int deleteByProjectId(long projectId);

}
