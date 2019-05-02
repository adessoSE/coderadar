package io.reflectoring.coderadar.graph.analyzer;

import io.reflectoring.coderadar.core.analyzer.domain.AnalyzingJob;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopAnalyzingRepository extends Neo4jRepository<AnalyzingJob, Long> {
}
