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
      "MATCH (p:ProjectEntity) WHERE ID(p) = {0} "
          + "WITH datetime({2}).epochMillis AS commitTime, p "
          + "MATCH (p)-->(c:CommitEntity)<--(f:FileEntity) WHERE "
          + "datetime(c.timestamp).epochMillis <= commitTime WITH p, f, commitTime "
          + "OPTIONAL MATCH (f)-[r:RENAMED_FROM]->(f2:FileEntity) WITH p, collect(DISTINCT f2) AS renames, commitTime "
          + "OPTIONAL MATCH (f)-[r:CHANGED_IN]->(c:CommitEntity) WHERE r.changeType = \"DELETE\" AND datetime(c.timestamp).epochMillis <= commitTime "
          + "WITH p, collect(DISTINCT f) AS deletes, commitTime, renames "
          + "MATCH (p)-[:CONTAINS*]->(f:FileEntity)-->(c:CommitEntity)<--(m:MetricValueEntity)<--(f) "
          + "WHERE datetime(c.timestamp).epochMillis <= commitTime AND NOT(f IN deletes OR f IN renames) AND m.name in {1} WITH f, m ORDER BY c.timestamp DESC "
          + "WITH f.path AS path, m.name AS name, head(collect(m.value)) AS value "
          + "RETURN name, SUM(value) AS value")
  List<MetricValueForCommitQueryResult> getMetricValuesForCommit(
      Long projectId, List<String> metricNames, String date);

  @Query(
      "MATCH (p:ProjectEntity) WHERE ID(p) = {0} "
          + "WITH datetime({2}).epochMillis AS commitTime, p "
          + "MATCH (p)-->(c:CommitEntity)<--(f:FileEntity) WHERE "
          + "datetime(c.timestamp).epochMillis <= commitTime WITH p, f, commitTime "
          + "OPTIONAL MATCH (f)-[r:RENAMED_FROM]->(f2:FileEntity) WITH p, collect(DISTINCT f2) AS renames, commitTime "
          + "OPTIONAL MATCH (f)-[r:CHANGED_IN]->(c:CommitEntity) WHERE r.changeType = \"DELETE\" AND datetime(c.timestamp).epochMillis <= commitTime "
          + "WITH p, collect(DISTINCT f) AS deletes, commitTime, renames "
          + "MATCH (p)-[:CONTAINS*]->(f:FileEntity)-->(c:CommitEntity)<--(m:MetricValueEntity)<--(f) "
          + "WHERE datetime(c.timestamp).epochMillis <= commitTime AND NOT(f IN deletes OR f IN renames) AND m.name in {1} WITH f, m ORDER BY c.timestamp DESC "
          + "WITH f.path AS path, m.name AS name, head(collect(m.value)) AS value "
          + "RETURN path, collect({name: name, value: value}) AS metrics ORDER BY path")
  List<MetricValueForCommitTreeQueryResult> getMetricTreeForCommit(
      Long projectId, List<String> metricNames, String date);
}
