package io.reflectoring.coderadar.graph.analyzer.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveMetricRepository extends Neo4jRepository {
}
