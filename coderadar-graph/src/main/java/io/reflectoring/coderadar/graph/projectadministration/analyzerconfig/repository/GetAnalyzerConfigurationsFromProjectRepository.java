package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GetAnalyzerConfigurationsFromProjectRepository
    extends Neo4jRepository<AnalyzerConfiguration, Long> {

  @Query("MATCH (p:Project)-[:HAS]->(c:AnalyzerConfiguration) WHERE ID(p) = {0} RETURN c")
  List<AnalyzerConfiguration> findByProject_Id(Long projectId);
}
