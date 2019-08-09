package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.analyzer.domain.AnalyzingJobEntity;
import java.util.Optional;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GetAnalyzingStatusRepository extends Neo4jRepository<AnalyzingJobEntity, Long> {
  @Query("MATCH (a:AnalyzingJobEntity)<-[:HAS]-(p:ProjectEntity) WHERE ID(p) = {0} RETURN a")
  Optional<AnalyzingJobEntity> findByProjectId(Long projectId);
}
