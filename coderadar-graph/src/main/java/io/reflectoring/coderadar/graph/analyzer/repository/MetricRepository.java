package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.analyzer.domain.MetricValueEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricRepository extends Neo4jRepository<MetricValueEntity, Long> {

  @Query(
      "MATCH (c:CommitEntity)<-[:VALID_FOR]-(m:MetricValueEntity) WHERE ID(c) = {0} DETACH DELETE m")
  void deleteMetricsInCommit(Long id);

  @Query(
      "MATCH (p:ProjectEntity)-->(f:FileEntity)-->(m:MetricValueEntity) WHERE ID(p) = {0} AND size((m)-[:VALID_FOR]-()) = 0 DETACH DELETE m")
  void removeMetricsWithoutCommits(Long id);
}
