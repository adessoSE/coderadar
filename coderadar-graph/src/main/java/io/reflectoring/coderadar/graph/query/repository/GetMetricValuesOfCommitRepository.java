package io.reflectoring.coderadar.graph.query.repository;

import io.reflectoring.coderadar.graph.analyzer.domain.CommitEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.MetricValueForCommitQueryResult;
import io.reflectoring.coderadar.graph.projectadministration.domain.MetricValueForCommitTreeQueryResult;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GetMetricValuesOfCommitRepository extends Neo4jRepository<CommitEntity, Long> {

  @Query(
      "MATCH (n:MetricValueEntity)-[:VALID_FOR*]->(c:CommitEntity) WHERE c.name = {0} AND n.name IN {1} RETURN DISTINCT n.name AS name, SUM(n.value) AS value")
  List<MetricValueForCommitQueryResult> getMetricValuesForCommit(
      String commitHash, List<String> metricNames);

  @Query(
      "MATCH (f:FileEntity)-[:MEASURED_BY]->(m:MetricValueEntity)-[:VALID_FOR]->(c:CommitEntity) WHERE c.name = {0} "
          + "AND m.name IN {1} return f.path AS path, collect(m {.name, .value}) AS metrics ORDER BY f.path DESC")
  List<MetricValueForCommitTreeQueryResult> getMetricTreeForCommit(
      String commitHash, List<String> metricNames);
}
