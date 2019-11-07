package io.reflectoring.coderadar.graph.query.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import io.reflectoring.coderadar.graph.query.domain.MetricValueForCommitQueryResult;
import io.reflectoring.coderadar.graph.query.domain.MetricValueForCommitTreeQueryResult;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GetMetricValuesOfCommitRepository extends Neo4jRepository<CommitEntity, Long> {

  @Query(
      "MATCH (p:ProjectEntity) WHERE ID(p) = {0} "
          + "MATCH (p)-[:CONTAINS_COMMIT]->(c)<-[:CHANGED_IN]-(f) WHERE "
          + "timestamp(c.timestamp) <= {2} WITH DISTINCT p, f "
          + "OPTIONAL MATCH (f)-[:RENAMED_FROM]->(f2) WITH p, collect(DISTINCT f2) AS renames "
          + "OPTIONAL MATCH (f)-[:CHANGED_IN {changeType: \"DELETE\"}]->(c:CommitEntity)<-[:CONTAINS_COMMIT]-(p) WHERE timestamp(c.timestamp) <= {2} "
          + "WITH p, collect(DISTINCT f) AS deletes, renames "
          + "MATCH (p)-[:CONTAINS_COMMIT]->(c)<-[:VALID_FOR]-(m)<-[:MEASURED_BY]-(f) "
          + "WHERE timestamp(c.timestamp) <= {2} AND NOT(f IN deletes OR f IN renames) AND m.name in {1} WITH f, m ORDER BY c.timestamp DESC "
          + "WITH f.path AS path, m.name AS name, head(collect(m.value)) AS value "
          + "RETURN name, SUM(value) AS value")
  List<MetricValueForCommitQueryResult> getMetricValuesForCommit(
      Long projectId, List<String> metricNames, Long date);

  @Query(
      "MATCH (p:ProjectEntity) WHERE ID(p) = {0} "
          + "MATCH (p)-[:CONTAINS_COMMIT]->(c)<-[:CHANGED_IN]-(f) WHERE "
          + "timestamp(c.timestamp) <= {2} WITH DISTINCT p, f "
          + "OPTIONAL MATCH (f)-[:RENAMED_FROM]->(f2) WITH p, collect(DISTINCT f2) AS renames "
          + "OPTIONAL MATCH (f)-[:CHANGED_IN {changeType: \"DELETE\"}]->(c:CommitEntity)<-[:CONTAINS_COMMIT]-(p) WHERE timestamp(c.timestamp) <= {2} "
          + "WITH p, collect(DISTINCT f) AS deletes, renames "
          + "MATCH (p)-[:CONTAINS_COMMIT]->(c)<-[:VALID_FOR]-(m)<-[:MEASURED_BY]-(f) "
          + "WHERE timestamp(c.timestamp) <= {2} AND NOT(f IN deletes OR f IN renames) AND m.name in {1} WITH f, m ORDER BY c.timestamp DESC "
          + "WITH f.path AS path, m.name AS name, head(collect(m.value)) AS value "
          + "RETURN path, collect({name: name, value: value}) AS metrics ORDER BY path")
  List<MetricValueForCommitTreeQueryResult> getMetricTreeForCommit(
      Long projectId, List<String> metricNames, Long date);
}
