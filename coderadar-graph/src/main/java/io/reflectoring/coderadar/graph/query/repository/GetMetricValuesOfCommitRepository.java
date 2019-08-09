package io.reflectoring.coderadar.graph.query.repository;

import io.reflectoring.coderadar.graph.analyzer.domain.CommitEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.MetricValueForCommitQueryResult;
import io.reflectoring.coderadar.graph.projectadministration.domain.MetricValueForCommitTreeQueryResult;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GetMetricValuesOfCommitRepository extends Neo4jRepository<CommitEntity, Long> {

  @Query(
      "MATCH (p:ProjectEntity)-->(f:FileEntity)-->(c:CommitEntity) "
          + "WITH datetime({2}).epochMillis AS commitTime, f, p, c "
          + "WHERE ID(p) = {0} AND datetime(c.timestamp).epochMillis <= commitTime WITH p, f, commitTime "
          + "OPTIONAL MATCH (f)-[r:CHANGED_IN]->() WHERE r.changeType = \"RENAME\" WITH p, collect(r.oldPath) AS paths, commitTime "
          + "OPTIONAL MATCH (f)-[r:CHANGED_IN]->(c:CommitEntity) WHERE r.changeType = \"DELETE\" AND datetime(c.timestamp).epochMillis <= commitTime  "
          + "WITH p, collect(ID(f)) AS ids, commitTime, paths "
          + "MATCH (p)-[:CONTAINS*]->(f:FileEntity)-[r:CHANGED_IN]->(c:CommitEntity)-[:VALID_FOR]-(m:MetricValueEntity)<-[:MEASURED_BY]-(f) "
          + "WHERE datetime(c.timestamp).epochMillis <= commitTime AND NOT(f.path IN paths) AND NOT(ID(f) IN ids) AND r.changeType <> \"DELETE\" AND m.name in {1} WITH c, f, paths, m ORDER BY c.timestamp DESC "
          + "WITH f.path AS path, m.name AS name, head(collect(m.value)) AS value "
          + "RETURN name, SUM(value) AS value")
  List<MetricValueForCommitQueryResult> getMetricValuesForCommit(
      Long projectId, List<String> metricNames, String date);

  @Query(
      "MATCH (p:ProjectEntity)-->(f:FileEntity)-->(c:CommitEntity) "
          + "WITH datetime({2}).epochMillis AS commitTime, f, p, c "
          + "WHERE ID(p) = {0} AND datetime(c.timestamp).epochMillis <= commitTime WITH p, f, commitTime "
          + "OPTIONAL MATCH (f)-[r:CHANGED_IN]->() WHERE r.changeType = \"RENAME\" WITH p, collect(r.oldPath) AS paths, commitTime "
          + "OPTIONAL MATCH (f)-[r:CHANGED_IN]->(c:CommitEntity) WHERE r.changeType = \"DELETE\" AND datetime(c.timestamp).epochMillis <= commitTime  "
          + "WITH p, collect(ID(f)) AS ids, commitTime, paths "
          + "MATCH (p)-[:CONTAINS*]->(f:FileEntity)-[r:CHANGED_IN]->(c:CommitEntity)-[:VALID_FOR]-(m:MetricValueEntity)<-[:MEASURED_BY]-(f) "
          + "WHERE datetime(c.timestamp).epochMillis <= commitTime AND NOT(f.path IN paths) AND NOT(ID(f) IN ids) AND r.changeType <> \"DELETE\" AND m.name in {1} WITH c, f, paths, m ORDER BY c.timestamp DESC "
          + "WITH f.path AS path, m.name AS name, head(collect(m.value)) AS value "
          + "RETURN path, collect({name: name, value: value}) AS metrics ORDER BY path")
  List<MetricValueForCommitTreeQueryResult> getMetricTreeForCommit(
      Long projectId, List<String> metricNames, String date);
}
