package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzingJob;
import java.util.Optional;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GetAnalyzingStatusRepository extends Neo4jRepository<AnalyzingJob, Long> {
  @Query()
  // TODO: Add query to find a AnalyzingJob in specific project.
  Optional<AnalyzingJob> findByProject_Id(Long projectId);
}
