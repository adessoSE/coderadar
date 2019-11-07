package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.FileEntity;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface FileRepository extends Neo4jRepository<FileEntity, Long> {

  @Query("MATCH (p:ProjectEntity)-[:CONTAINS*]->(f:FileEntity) WHERE ID(p) = {0} RETURN f")
  List<FileEntity> findAllinProject(Long projectId);

  /**
   * @param commit1Time The time of the first commit
   * @param commit2Time The time of the second commit
   * @param projectId The project id
   * @return The paths of the files that have been modified
   */
  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS*]->(f:FileEntity)-[r:CHANGED_IN]->(c:CommitEntity) WHERE ID(p) = {2} "
          + "AND timestamp(c.timestamp) <= {1}  "
          + "AND timestamp(c.timestamp) > {0} AND r.changeType = \"MODIFY\" "
          + "RETURN DISTINCT f.path")
  List<String> getFilesModifiedBetweenCommits(Long commit1Time, Long commit2Time, Long projectId);

  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS*]->(f:FileEntity)-[r:CHANGED_IN]->(c:CommitEntity) WHERE f.path = {0} AND timestamp(c.timestamp) <= {2}  "
          + "AND timestamp(c.timestamp) > {1} AND ID(p) = {3} AND r.changeType = \"RENAME\""
          + " RETURN head(collect(DISTINCT r)).oldPath")
  String wasRenamedBetweenCommits(String path, Long commit1Time, Long commit2Time, Long projectId);

  @Query("MATCH (f:FileEntity) WHERE ID(f) IN {0} RETURN f")
  List<FileEntity> findFilesByIds(List<Long> fileIds);
}
