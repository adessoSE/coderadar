package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GetAnalyzerConfigurationsFromProjectRepository
    extends Neo4jRepository<AnalyzerConfiguration, Long> {

  default List<AnalyzerConfiguration> findByProject_Id(Long projectId) {
    List<AnalyzerConfiguration> result = new ArrayList<>();
    Iterable<AnalyzerConfiguration> analyzerConfigurations = findAll();
    for (AnalyzerConfiguration analyzerConfiguration : analyzerConfigurations) {
      if (analyzerConfiguration.getProject().getId().equals(projectId)) {
        result.add(analyzerConfiguration);
      }
    }
    return result;
  }
}
