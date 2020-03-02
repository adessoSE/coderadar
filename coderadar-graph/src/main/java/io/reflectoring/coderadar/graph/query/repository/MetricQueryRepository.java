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
      "MATCH (p)-[:CONTAINS_COMMIT]->(c) WHERE ID(p) = {0} AND c.name = {1} WITH c LIMIT 1 "
          + "CALL apoc.path.subgraphNodes(c, {relationshipFilter:'IS_CHILD_OF>'}) YIELD node WITH node as c ORDER BY c.timestamp DESC WITH collect(c) as commits "
          + "CALL apoc.cypher.run('UNWIND commits as c OPTIONAL MATCH (f)<-[:RENAMED_FROM]-()-[:CHANGED_IN]->(c) RETURN collect(f) as renames', {commits: commits}) "
          + "YIELD value WITH commits, value.renames as renames "
          + "CALL apoc.cypher.run('UNWIND commits as c OPTIONAL MATCH (f)-[:CHANGED_IN {changeType: \"DELETE\"}]->(c) "
          + "RETURN collect(f) as deletes', {commits: commits}) YIELD value WITH commits, renames, value.deletes as deletes "
          + "UNWIND commits as c "
          + "MATCH (f)-[:MEASURED_BY]->(m)-[:VALID_FOR]->(c) WHERE "
          + "NOT(f IN deletes OR f IN renames) AND m.name in {2} WITH f.path as path, m.name as name, head(collect(m.value)) as value WHERE value <> 0 "
          + "RETURN name, SUM(value) AS value ORDER BY name")
  @NonNull
  List<MetricValueForCommitQueryResult> getMetricValuesForCommit(
      @NonNull Long projectId, @NonNull String commitHash, @NonNull List<String> metricNames);

  /**
   * Metrics for each file are collected as string in the following format: "metricName=value" The
   * string is then split in the adapter. This greatly reduces HashMap usage.
   *
   * <p>NOTE: uses APOC.
   *
   * @param projectId The project id.
   * @param commitHash The hash of the commit.
   * @param metricNames The names of the metrics needed.
   * @return Metrics for each file in the given commit.
   */
  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c) WHERE ID(p) = {0} AND c.name = {1} WITH c LIMIT 1 "
          + "CALL apoc.path.subgraphNodes(c, {relationshipFilter:'IS_CHILD_OF>'}) YIELD node WITH node as c ORDER BY c.timestamp DESC WITH collect(c) as commits "
          + "CALL apoc.cypher.run('UNWIND commits as c OPTIONAL MATCH (f)<-[:RENAMED_FROM]-()-[:CHANGED_IN]->(c) RETURN collect(f) as renames', {commits: commits}) "
          + "YIELD value WITH commits, value.renames as renames "
          + "CALL apoc.cypher.run('UNWIND commits as c OPTIONAL MATCH (f)-[:CHANGED_IN {changeType: \"DELETE\"}]->(c) "
          + "RETURN collect(f) as deletes', {commits: commits}) YIELD value WITH commits, renames, value.deletes as deletes "
          + "UNWIND commits as c "
          + "MATCH (f)-[:MEASURED_BY]->(m)-[:VALID_FOR]->(c) WHERE "
          + "NOT(f IN deletes OR f IN renames) AND m.name in {2} WITH f.path as path, m.name as name, head(collect(m.value)) as value ORDER BY path, name WHERE value <> 0 "
          + "RETURN path, collect(name + \"=\" + value) AS metrics ORDER BY path")
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

  /**
   * Metrics for each file are collected as string in the following format: "metricName=value" The
   * string is then split in the adapter. This greatly reduces HashMap usage. File paths are
   * returned along with their corresponding git hashes in the following format:
   * "filePathObjectHash".
   *
   * <p>NOTE: uses APOC.
   *
   * @param projectId The project id.
   * @param commitHash The hash of the commit.
   * @param metricNames The names of the metrics needed.
   * @return Metrics for each file in the given commit.
   */
  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c) WHERE ID(p) = {0} AND c.name = {1} WITH c LIMIT 1 "
          + "CALL apoc.path.subgraphNodes(c, {relationshipFilter:'IS_CHILD_OF>'}) YIELD node WITH node as c ORDER BY c.timestamp DESC WITH collect(c) as commits "
          + "CALL apoc.cypher.run('UNWIND commits as c OPTIONAL MATCH (f)<-[:RENAMED_FROM]-()-[:CHANGED_IN]->(c) RETURN collect(f) as renames', {commits: commits}) "
          + "YIELD value WITH commits, value.renames as renames "
          + "CALL apoc.cypher.run('UNWIND commits as c OPTIONAL MATCH (f)-[:CHANGED_IN {changeType: \"DELETE\"}]->(c) "
          + "RETURN collect(f) as deletes', {commits: commits}) YIELD value WITH commits, renames, value.deletes as deletes "
          + "UNWIND commits as c "
          + "MATCH (f)-[:MEASURED_BY]->(m)-[:VALID_FOR]->(c) WHERE "
          + "NOT(f IN deletes OR f IN renames)  AND m.name IN {2} WITH f.path as path, head(collect(f.objectHash)) as hash, m.name as name, "
          + "head(collect(m.value)) as value ORDER BY path, name WHERE value <> 0 "
          + "RETURN path + head(collect(hash)) as path, collect(name + \"=\" + value) as metrics ORDER BY path")
  @NonNull
  List<MetricValueForCommitTreeQueryResult> getMetricTreeForCommitWithFileHashes(
      @NonNull Long projectId, @NonNull String commitHash, @NonNull List<String> metricNames);
}
