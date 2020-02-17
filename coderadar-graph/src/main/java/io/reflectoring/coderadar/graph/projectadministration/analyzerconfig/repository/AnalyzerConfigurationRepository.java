package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository;

import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzerConfigurationEntity;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalyzerConfigurationRepository
    extends Neo4jRepository<AnalyzerConfigurationEntity, Long> {

  /**
   * @param projectId The project id.
   * @return All analyzer configurations in a project.
   */
  @Query("MATCH (p)-[:HAS]->(c:AnalyzerConfigurationEntity) WHERE ID(p) = {0} RETURN c")
  @NonNull
  List<AnalyzerConfigurationEntity> findByProjectId(@NonNull Long projectId);
}
