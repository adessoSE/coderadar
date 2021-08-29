package io.reflectoring.coderadar.graph.query.repository;

import io.reflectoring.coderadar.graph.analyzer.domain.MetricValueEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MetricQueryRepository extends Neo4jRepository<MetricValueEntity, Long> {

  /**
   * NOTE: uses APOC.
   *
   * @param projectId The project id.
   * @param commitHash The hash of the commit.
   * @param metricNames The names of the metrics needed.
   * @return All metric values aggregated for the entire file tree in a single commit.
   */
  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE ID(p) = $0 AND c.hash = $1 WITH c LIMIT 1 "
          + "CALL apoc.path.subgraphNodes(c, {relationshipFilter:'IS_CHILD_OF>'}) YIELD node WITH node as c ORDER BY c.timestamp DESC WITH collect(c) as commits "
          + "CALL { "
          + "    WITH commits "
          + "    UNWIND commits as c "
          + "    OPTIONAL MATCH (f)<-[:RENAMED_FROM]-()-[:CHANGED_IN]->(c) RETURN f as exclude "
          + "    UNION "
          + "    OPTIONAL MATCH (f)-[:DELETED_IN]->(c) RETURN f as exclude "
          + "} "
          + "WITH commits, collect(exclude) as excludes "
          + "UNWIND commits as c "
          + "MATCH (f)-[:MEASURED_BY]->(m)-[:VALID_FOR]->(c) WHERE NOT (f IN excludes) AND m.name IN $2 "
          + "WITH f.path as path, m.name as name, head(collect(m.value)) as value WHERE value <> 0 "
          + "RETURN name, SUM(value) AS value ORDER BY name")
  @NonNull
  List<Map<String, Object>> getMetricValuesForCommit(
      long projectId, long commitHash, @NonNull int[] metricNames);

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
      "MATCH (p)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE ID(p) = $0 AND c.hash = $1 WITH c LIMIT 1 "
          + "CALL apoc.path.subgraphNodes(c, {relationshipFilter:'IS_CHILD_OF>'}) YIELD node " +
              "WITH node as c ORDER BY c.timestamp DESC WITH collect(c) as commits "
          + "CALL { "
          + "    WITH commits "
          + "    UNWIND commits as c "
          + "    OPTIONAL MATCH (f)<-[:RENAMED_FROM]-()-[:CHANGED_IN]->(c) RETURN f as exclude "
          + "    UNION "
          + "    OPTIONAL MATCH (f)-[:DELETED_IN]->(c) RETURN f as exclude "
          + "} "
          + "WITH commits, collect(exclude) as excludes "
          + "UNWIND commits as c "
          + "MATCH (f)-[:MEASURED_BY]->(m)-[:VALID_FOR]->(c) WHERE NOT (f IN excludes) AND m.name IN $2 "
          + "WITH f.path as path, m.name as name, head(collect(m.value)) as value WHERE value <> 0 "
          + "RETURN path, collect(name + \"=\" + value) AS metrics ORDER BY path")
  @NonNull
  List<Map<String, Object>> getMetricTreeForCommit(
      long projectId, long commitHash, @NonNull int[] metricNames);

  /**
   * @param projectId The project id.
   * @return All of the available metrics in the project/
   */
  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->()<-[:VALID_FOR]-(mv) WHERE ID(p) = $0 RETURN DISTINCT mv.name")
  @NonNull
  List<Integer> getAvailableMetricsInProject(long projectId);

  /**
   * NOTE: This query is currently unused, but I believe it might be useful in the future.
   *
   * @param projectId The project id.
   * @param commitHash The hash of the commit.
   * @param metricNames The names of the metrics needed.
   * @return Metrics and their corresponding findings for each file in the given commit.
   */
  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE ID(p) = $0 AND c.hash = $1 WITH c LIMIT 1 "
          + "CALL apoc.path.subgraphNodes(c, {relationshipFilter:'IS_CHILD_OF>'}) YIELD node WITH node as c ORDER BY c.timestamp DESC WITH collect(c) as commits "
          + "CALL { "
          + "    WITH commits "
          + "    UNWIND commits as c "
          + "    OPTIONAL MATCH (f)<-[:RENAMED_FROM]-()-[:CHANGED_IN]->(c) RETURN f as exclude "
          + "    UNION "
          + "    OPTIONAL MATCH (f)-[:DELETED_IN]->(c) RETURN f as exclude "
          + "} "
          + "WITH commits, collect(exclude) as excludes "
          + "UNWIND commits as c "
          + "MATCH (f)-[:MEASURED_BY]->(m)-[:VALID_FOR]->(c) WHERE NOT (f IN excludes) AND m.name IN $2 "
          + "WITH f.path as path, m.name as name, head(collect(m.value)) as value WHERE value <> 0 "
          + "RETURN path, collect(name + \"=\" + value + location) as metrics ORDER BY path")
  List<Map<String, Object>> getMetricTreeForCommitWithFindings(
      long projectId, long commitHash, @NonNull int[] metricNames);

  /**
   * @param projectId The project id.
   * @param commitHash The hash of the commit.
   * @return A list of all the files in the given commit.
   */
  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE ID(p) = $0 AND c.hash = $1 WITH c LIMIT 1 "
          + "CALL apoc.path.subgraphNodes(c, {relationshipFilter:'IS_CHILD_OF>'}) YIELD node WITH node as c ORDER BY c.timestamp DESC WITH collect(c) as commits "
          + "CALL { "
          + "    WITH commits "
          + "    UNWIND commits as c "
          + "    OPTIONAL MATCH (f)<-[:RENAMED_FROM]-()-[:CHANGED_IN]->(c) RETURN f as exclude "
          + "    UNION "
          + "    OPTIONAL MATCH (f)-[:DELETED_IN]->(c) RETURN f as exclude "
          + "} "
          + "WITH commits, collect(exclude) as excludes "
          + "UNWIND commits as c "
          + "MATCH (f)-[:CHANGED_IN]->(c) WHERE NOT (f IN excludes) "
          + "RETURN DISTINCT f.path as path")
  List<String> getFileTreeForCommit(long projectId, long commitHash);

  /**
   * @param projectId The project id.
   * @param commitHash The hash of the commit.
   * @param filepath The full path of the file.
   * @return The metrics and their corresponding files for the given file.
   */
  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE ID(p) = $0 AND c.hash = $1 WITH c LIMIT 1 "
          + "CALL apoc.path.subgraphNodes(c, {relationshipFilter:'IS_CHILD_OF>'}) YIELD node WITH node as c ORDER BY c.timestamp DESC "
          + "MATCH (f)-[r:CHANGED_IN]->(c) WHERE f.path = $2 WITH f, c LIMIT 1 "
          + "MATCH (f)-[:MEASURED_BY]->(m)-[:VALID_FOR]->(c) "
          + "WITH m.name as name, m.value as value, m.findings as findings WHERE m.value <> 0 "
          + "RETURN name, value, findings ORDER BY name")
  List<Map<String, Object>> getMetricsAndFindingsForCommitAndFilepath(
      long projectId, long commitHash, String filepath);

  /**
   * @param projectId The project id.
   * @param commitHash The hash of the commit.
   * @return A list of all the files in the given commit.
   */
  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE ID(p) = $0 AND c.hash = $1 WITH c LIMIT 1 "
          + "MATCH (f)-[:CHANGED_IN|DELETED_IN]->(c) "
          + "RETURN f.path as path")
  List<String> getFilesChangedInCommit(long projectId, long commitHash);
}
