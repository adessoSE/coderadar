package org.wickedsource.coderadar.analyzer.domain;

import org.springframework.data.repository.CrudRepository;

public interface AnalyzerConfigurationFileRepository extends CrudRepository<AnalyzerConfigurationFile, Long> {

    AnalyzerConfigurationFile findByAnalyzerConfigurationProjectIdAndAnalyzerConfigurationId(Long projectId, Long analyzerConfigurationId);

}
