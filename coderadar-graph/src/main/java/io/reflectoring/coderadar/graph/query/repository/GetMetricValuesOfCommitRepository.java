package io.reflectoring.coderadar.graph.query.repository;

import io.reflectoring.coderadar.graph.analyzer.domain.CommitEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.MetricValueForCommitQueryResult;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GetMetricValuesOfCommitRepository extends Neo4jRepository<CommitEntity, Long> {

    @Query("MATCH (p:ProjectEntity)-[:CONTAINS*]->(f:FileEntity)-[r:CHANGED_IN]->(c:CommitEntity) " +
            "WHERE r.changeType = \"RENAME\" AND ID(p) = {0} AND datetime(c.timestamp).epochMillis <= datetime({2}).epochMillis WITH p, collect(r.oldPath) as paths " +
            "MATCH (p)-[:CONTAINS*]->(f:FileEntity)-[r:CHANGED_IN]->(c:CommitEntity)-[:VALID_FOR]-(m:MetricValueEntity)<-[:MEASURED_BY]-(f) " +
            "WHERE datetime(c.timestamp).epochMillis <= datetime({2}).epochMillis AND  NOT(f.path IN paths) AND m.name in {1} WITH c, f, paths, m ORDER BY c.timestamp DESC " +
            "WITH f.path as path, m.name as name, head(collect(m.value)) as value " +
            "RETURN DISTINCT name, SUM(value) as value")
    List<MetricValueForCommitQueryResult> getMetricValuesForCommit(Long projectId, List<String> metricNames, String date);

/*    @Query("")
    List<MetricValueForCommitTreeQueryResult> getMetricTreeForCommit(String commitHash, List<String> metricNames);*/

}
