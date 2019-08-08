package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.analyzer.domain.MetricValueEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface MetricValueRepository extends Neo4jRepository<MetricValueEntity, Long> {
    @Query("MATCH (p:ProjectEntity)-[:CONTAINS*]->(f:FileEntity)-[:MEASURED_BY*]->(m:MetricValueEntity) WHERE ID(p) = {0} DETACH DELETE m")
    void deleteAllMetricValuesFromProject(Long projectId);
}
