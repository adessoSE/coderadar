package io.reflectoring.coderadar.graph.query.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import io.reflectoring.coderadar.graph.query.domain.MetricValueForCommitQueryResult;
import io.reflectoring.coderadar.graph.query.domain.MetricValueForCommitTreeQueryResult;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricQueryRepository extends Neo4jRepository<CommitEntity, Long> {

  /**
   * NOTE: uses APOC.
   *
   * @param projectId The project id.
   * @param commitHash The hash of the commit.
   * @param metricNames The names of the metrics needed.
   * @return All metric values aggregated for the entire file tree in a single commit.
   */
  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c) WHERE ID(p) = {0} AND c.name = {1} WITH c "
          + "CALL apoc.path.subgraphNodes(c, {relationshipFilter:'IS_CHILD_OF>'}) YIELD node WITH collect(node) as commits "
          + "OPTIONAL MATCH (f)<-[:RENAMED_FROM]-()-[:CHANGED_IN {changeType: \"RENAME\"}]->(c)<-[:CONTAINS_COMMIT]-(p) WHERE ID(p) = {0} AND c IN commits WITH collect(f) as renames, commits "
          + "OPTIONAL MATCH (f)-[:CHANGED_IN {changeType: \"DELETE\"}]->(c)<-[:CONTAINS_COMMIT]-(p) WHERE ID(p) = {0} AND c IN commits WITH DISTINCT collect(f) as deletes, renames, commits "
          + "UNWIND commits as c "
          + "MATCH (c)<-[:CHANGED_IN]-(f)-[:MEASURED_BY]->(m)-[:VALID_FOR]->(c) WHERE "
          + "NOT(f IN deletes OR f IN renames) AND m.name in {2} WITH f, m ORDER BY c.timestamp DESC "
          + "WITH f.path AS path, m.name AS name, head(collect(m.value)) AS value WHERE value <> 0 "
          + "RETURN name, SUM(value) AS value ORDER BY name ")
  @NonNull
  List<MetricValueForCommitQueryResult> getMetricValuesForCommit(
      @NonNull Long projectId, @NonNull String commitHash, @NonNull List<String> metricNames);

  /**
   * Metrics for each file are collected as string in the following format: "metricName=value" The
   * string is then split in the adapter. This greatly reduces HashMap usage. NOTE: uses APOC.
   *
   * @param projectId The project id.
   * @param commitHash The hash of the commit.
   * @param metricNames The names of the metrics needed.
   * @return Metrics for each file in the given commit.
   */
  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c) WHERE ID(p) = {0} AND c.name = {1} WITH c "
          + "CALL apoc.path.subgraphNodes(c, {relationshipFilter:'IS_CHILD_OF>'}) YIELD node WITH collect(node) as commits "
          + "OPTIONAL MATCH (f)<-[:RENAMED_FROM]-()-[:CHANGED_IN {changeType: \"RENAME\"}]->(c)<-[:CONTAINS_COMMIT]-(p) WHERE ID(p) = {0} AND c IN commits WITH collect(f) as renames, commits "
          + "OPTIONAL MATCH (f)-[:CHANGED_IN {changeType: \"DELETE\"}]->(c)<-[:CONTAINS_COMMIT]-(p) WHERE ID(p) = {0} AND c IN commits WITH DISTINCT collect(f) as deletes, renames, commits "
          + "UNWIND commits as c "
          + "MATCH (c)<-[:CHANGED_IN]-(f)-[:MEASURED_BY]->(m)-[:VALID_FOR]->(c) WHERE "
          + "NOT(f IN deletes OR f IN renames) AND m.name in {2} WITH f, m ORDER BY c.timestamp DESC "
          + "WITH f.path AS path, m.name AS name, head(collect(m.value)) AS value ORDER BY path, name WHERE value <> 0 "
          + "RETURN path, collect(name + \"=\" + value) AS metrics ")
  @NonNull
  List<MetricValueForCommitTreeQueryResult> getMetricTreeForCommit(
      @NonNull Long projectId, @NonNull String commitHash, @NonNull List<String> metricNames);

  /**
   * @param projectId The project id.
   * @return All of the available metrics in the project/
   */
  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->()<-[:VALID_FOR]-(mv) WHERE ID(p) = {0} RETURN DISTINCT mv.name")
  @NonNull
  List<String> getAvailableMetricsInProject(@NonNull Long projectId);
}
