package io.reflectoring.coderadar.graph.analyzer;

import io.reflectoring.coderadar.core.analyzer.domain.AnalyzingJob;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GetAnalyzingStatusRepository extends Neo4jRepository<AnalyzingJob, Long> {
    @Query() // TODO: Add query to find a AnalyzingJob in spezific project.
    Optional<AnalyzingJob> findByProject_Id(Long projectId);
}
