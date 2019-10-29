package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.analyzer.domain.MetricValueEntity;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricRepository extends Neo4jRepository<MetricValueEntity, Long> {

  @Query(
      "MATCH (c:CommitEntity)<-[:VALID_FOR]-(m:MetricValueEntity) WHERE ID(c) = {0} DETACH DELETE m")
  void deleteMetricsInCommit(Long id);

  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS*]->(f:FileEntity)-[:MEASURED_BY]->(m) AND size((m)-[:VALID_FOR]->()) = 0 DETACH DELETE m")
  void removeMetricsWithoutCommits(Long id);

  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS*]->(:FileEntity)-[:MEASURED_BY]->(m:MetricValueEntity) "
          + "WHERE ID(p) = {0} RETURN m")
  List<MetricValueEntity> findByProjectId(Long projectId);

  @Query(
      "MATCH (f:FileEntity)-[:CHANGED_IN]->(c:CommitEntity)<-[:VALID_FOR]-(m:MetricValueEntity) WHERE ID(f) = {0} AND c.name = {1}"
          + " RETURN m")
  List<MetricValueEntity> findMetricsByFileAndCommitName(Long id, String commitHash);
}
