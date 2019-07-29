package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.AnalyzerConfigurationEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GetAnalyzerConfigurationsFromProjectRepository
    extends Neo4jRepository<AnalyzerConfigurationEntity, Long> {

  @Query(
      "MATCH (p:ProjectEntity)-[:HAS]->(c:AnalyzerConfigurationEntity) WHERE ID(p) = {projectId} RETURN c")
  List<AnalyzerConfigurationEntity> findByProjectId(@Param("projectId") Long projectId);
}
