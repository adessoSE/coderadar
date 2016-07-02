package org.wickedsource.coderadar.project.domain;

import org.springframework.data.repository.CrudRepository;

public interface AnalyzingStrategyRepository extends CrudRepository<AnalyzingStrategy, Long> {

    AnalyzingStrategy findByProjectId(long projectId);

    void deleteByProjectId(long projectId);

}
