package io.reflectoring.coderadar.graph.analyzer.repository;

import java.util.Optional;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzingJob;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GetAnalyzingStatusRepository extends Neo4jRepository<AnalyzingJob, Long> {
  @Query()
  // TODO: Add query to find a AnalyzingJob in spezific project.
  Optional<AnalyzingJob> findByProject_Id(Long projectId);
}
