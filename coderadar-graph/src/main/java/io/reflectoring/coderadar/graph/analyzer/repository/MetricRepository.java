package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.analyzer.domain.MetricValueEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetricRepository extends Neo4jRepository<MetricValueEntity, Long> {

  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS*]->(:FileEntity)-[:MEASURED_BY]->(m:MetricValueEntity) "
          + "WHERE ID(p) = {0} RETURN m")
  List<MetricValueEntity> findByProjectId(@NonNull Long projectId);
}
