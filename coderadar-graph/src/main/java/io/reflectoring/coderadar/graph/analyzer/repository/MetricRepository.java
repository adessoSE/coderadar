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

  /**
   * NOTE: only used in tests. WILL cause an out of memory exception if used on a sufficiently large
   * project.
   *
   * @param projectId The project id.
   * @return All of the metric values in a project.
   */
  @Query("MATCH (p)-[:CONTAINS*]->()-[:MEASURED_BY]->(m) WHERE ID(p) = {0} RETURN m")
  List<MetricValueEntity> findByProjectId(@NonNull Long projectId);

  /**
   * Creates [:MEASURED_BY] relationships between metric values and files and [:VALID_FOR]
   * relationships between metric values and commits.
   *
   * @param commitAndFileRels A list of maps, each containing the id of an existing
   *     MetricValueEntity ("metricId"), an existing FileEntity ("fileId") and an existing
   *     CommitEntity ("commitId").
   */
  @Query(
      "UNWIND {0} as x "
          + "MATCH (m) WHERE ID(m) = x.metricId "
          + "MATCH (f) WHERE ID(f) = x.fileId "
          + "MATCH (c) WHERE ID(c) = x.commitId "
          + "CREATE (f)-[:MEASURED_BY]->(m)-[:VALID_FOR]->(c)")
  void createFileAndCommitRelationships(List<HashMap<String, Object>> commitAndFileRels);
}
