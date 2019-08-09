package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.analyzer.domain.FileEntity;
import io.reflectoring.coderadar.graph.analyzer.domain.FileToCommitRelationshipEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface FileRepository extends Neo4jRepository<FileEntity, Long> {

  @Query("MATCH (p:ProjectEntity)-->(f:FileEntity) WHERE ID(p) = {1} AND f.path = {0} RETURN f")
  FileEntity findByPathInProject(String path, Long projectId);

  @Query(
      "MATCH (p:ProjectEntity)-->(f:FileEntity)-[r:CHANGED_IN]->(c:CommitEntity) WHERE f.path = {0} AND c.name = {1} AND ID(p) = {2}"
          + " RETURN r.changeType as changeType, r.oldPath as oldPath")
  FileToCommitRelationshipEntity findByPathAndCommitNameInProject(
      String path, String commitHash, Long projectId);

  @Query(
      "MATCH (p:ProjectEntity)-->(f:FileEntity)-[r:CHANGED_IN]->(c:CommitEntity) WHERE f.path = {0} AND datetime(c.timestamp).epochMillis <= datetime({2}).epochMillis  "
          + "AND datetime(c.timestamp).epochMillis > datetime({1}).epochMillis AND ID(p) = {3} AND r.changeType = \"MODIFY\""
          + " RETURN size(collect(r)) > 0")
  Boolean wasModifiedBetweenCommits(
      String path, String commit1Time, String commit2Time, Long projectId);

  @Query(
      "MATCH (p:ProjectEntity)-->(f:FileEntity)-[r:CHANGED_IN]->(c:CommitEntity) WHERE f.path = {0} AND datetime(c.timestamp).epochMillis <= datetime({2}).epochMillis  "
          + "AND datetime(c.timestamp).epochMillis > datetime({1}).epochMillis AND ID(p) = {3} AND r.changeType = \"RENAME\""
          + " RETURN head(collect(r)).oldPath")
  String wasRenamedBetweenCommits(
      String path, String commit1Time, String commit2Time, Long projectId);
}
