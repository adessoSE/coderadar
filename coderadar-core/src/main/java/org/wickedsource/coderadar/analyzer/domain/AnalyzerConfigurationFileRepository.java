package org.wickedsource.coderadar.analyzer.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AnalyzerConfigurationFileRepository
    extends CrudRepository<AnalyzerConfigurationFile, Long> {

  AnalyzerConfigurationFile findByAnalyzerConfigurationProjectIdAndAnalyzerConfigurationId(
      Long projectId, Long analyzerConfigurationId);

  @Query(
      "delete AnalyzerConfigurationFile f where f.analyzerConfiguration.id in (select a.id from AnalyzerConfiguration a where a.project.id = :projectId)")
  @Modifying
  int deleteByProjectId(@Param("projectId") Long projectId);
}
