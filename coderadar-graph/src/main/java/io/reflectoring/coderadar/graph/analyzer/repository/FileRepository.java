package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.analyzer.domain.FileEntity;
import io.reflectoring.coderadar.graph.analyzer.domain.MetricValueEntity;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface FileRepository extends Neo4jRepository<FileEntity, Long> {

  @Query("MATCH (p:ProjectEntity)-[:CONTAINS*]->(f:FileEntity) WHERE ID(p) = {0} RETURN f")
  List<FileEntity> findAllinProject(Long projectId);

  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS*]->(f:FileEntity)-[r:CHANGED_IN]->(c:CommitEntity) WHERE f.path = {0} AND datetime(c.timestamp).epochMillis <= datetime({2}).epochMillis  "
          + "AND datetime(c.timestamp).epochMillis > datetime({1}).epochMillis AND ID(p) = {3} AND r.changeType = \"MODIFY\""
          + " RETURN size(collect(r)) > 0")
  Boolean wasModifiedBetweenCommits(
      String path, String commit1Time, String commit2Time, Long projectId);

  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS*]->(f:FileEntity)-[r:CHANGED_IN]->(c:CommitEntity) WHERE f.path = {0} AND datetime(c.timestamp).epochMillis <= datetime({2}).epochMillis  "
          + "AND datetime(c.timestamp).epochMillis > datetime({1}).epochMillis AND ID(p) = {3} AND r.changeType = \"RENAME\""
          + " RETURN head(collect(r)).oldPath")
  String wasRenamedBetweenCommits(
      String path, String commit1Time, String commit2Time, Long projectId);

  @Query(
      "MATCH (f:FileEntity)-[r:CHANGED_IN]->(c:CommitEntity)<-[:VALID_FOR]-(m:MetricValueEntity) WHERE ID(f) = {0} AND c.name = {1}"
          + " RETURN m")
  List<MetricValueEntity> findMetricsByFileAndCommitName(Long id, String commitHash);

  @Query(
      "MATCH (p:ProjectEntity)-[:HAS*]->(f:FileEntity) WHERE ID(p) = {0} AND size((f)-[:CHANGED_IN]-()) = 0 DETACH DELETE f")
  void removeFilesWithoutCommits(Long id);

  @Query("MATCH (f:FileEntity) WHERE ID(f) IN {0} RETURN f")
  List<FileEntity> findFilesByIds(List<Long> fileIds);
}
