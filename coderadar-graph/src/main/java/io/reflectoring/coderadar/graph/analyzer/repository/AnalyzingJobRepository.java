package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzingJobEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnalyzingJobRepository extends Neo4jRepository<AnalyzingJobEntity, Long> {
  @Query("MATCH (p:ProjectEntity)-[:HAS]->(a:AnalyzingJobEntity) WHERE ID(p) = {0} RETURN a")
  Optional<AnalyzingJobEntity> findByProjectId(Long projectId);
}
