package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository;

import io.reflectoring.coderadar.projectadministration.domain.AnalyzerConfiguration;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GetAnalyzerConfigurationsFromProjectRepository
    extends Neo4jRepository<AnalyzerConfiguration, Long> {

  @Query("MATCH (p:Project)-[:HAS]->(c:AnalyzerConfiguration) WHERE ID(p) = {projectId} RETURN c")
  List<AnalyzerConfiguration> findByProject_Id(@Param("projectId") Long projectId);
}
