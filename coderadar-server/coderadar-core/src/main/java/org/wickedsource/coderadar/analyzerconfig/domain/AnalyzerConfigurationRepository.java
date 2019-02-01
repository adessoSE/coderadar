package org.wickedsource.coderadar.analyzerconfig.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface AnalyzerConfigurationRepository
		extends CrudRepository<AnalyzerConfiguration, Long> {

	AnalyzerConfiguration findByProjectIdAndAnalyzerName(Long projectId, String analyzerName);

	int countByProjectIdAndAnalyzerName(Long projectId, String analyzerName);

	List<AnalyzerConfiguration> findByProjectId(Long projectId);

	int deleteByProjectIdAndId(Long projectId, Long analyzerId);

	AnalyzerConfiguration findByProjectIdAndId(Long projectId, Long analyzerConfigurationId);

	int deleteByProjectId(Long id);

	int countByProjectId(Long projectId);
}
