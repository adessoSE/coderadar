package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import java.util.List;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GetAnalyzerConfigurationsFromProjectRepository
    extends Neo4jRepository<AnalyzerConfiguration, Long> {
  List<AnalyzerConfiguration> findByProject_Id(Long projectId);
}
