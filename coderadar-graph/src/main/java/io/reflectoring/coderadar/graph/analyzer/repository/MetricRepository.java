package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.analyzer.domain.MetricValueEntity;
import java.util.HashMap;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricRepository extends Neo4jRepository<MetricValueEntity, Long> {

  @Query("MATCH (p)-[:CONTAINS*]->()-[:MEASURED_BY]->(m) " + "WHERE ID(p) = {0} RETURN m")
  List<MetricValueEntity> findByProjectId(@NonNull Long projectId);

  @Query(
      "UNWIND {0} as x "
          + "MATCH (m) WHERE ID(m) = x.metricId "
          + "MATCH (f) WHERE ID(f) = x.fileId "
          + "MATCH (c) WHERE ID(c) = x.commitId "
          + "CREATE (f)-[:MEASURED_BY]->(m)-[:VALID_FOR]->(c)")
  void createFileAndCommitRelationsips(List<HashMap<String, Object>> commitAndFileRels);
}
