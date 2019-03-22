package org.wickedsource.coderadar.projectadministration.port.driver.analyzerconfig;

public interface DeleteAnalyzerConfigurationUseCase {
    void deleteAnalyzerConfiguration(Long projectId, Long analyzerConfigurationId);
}
