package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.analyzer.domain.MetricValue;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveMetricRepository extends Neo4jRepository<MetricValue, Long> {}
