package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.analyzer.domain.MetricValueEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveMetricRepository extends Neo4jRepository<MetricValueEntity, Long> {}
