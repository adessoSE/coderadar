package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.FileEntity;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;

public interface FileRepository extends Neo4jRepository<FileEntity, Long> {

  @Query("MATCH (p:ProjectEntity)-[:CONTAINS*]->(f:FileEntity) WHERE ID(p) = {0} RETURN f")
  @NonNull
  List<FileEntity> findAllinProject(@NonNull Long projectId);

  /**
   * @param commit1Time The time of the first commit
   * @param commit2Time The time of the second commit
   * @param projectId The project id
   * @return The paths of the files that have been modified
   */
  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS*]->(f:FileEntity)-[r:CHANGED_IN]->(c:CommitEntity) WHERE ID(p) = {2} "
          + "AND c.timestamp <= {1}  "
          + "AND c.timestamp > {0} AND r.changeType = \"MODIFY\" "
          + "RETURN DISTINCT f.path")
  @NonNull
  List<String> getFilesModifiedBetweenCommits(
      @NonNull Long commit1Time, @NonNull Long commit2Time, @NonNull Long projectId);

  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS*]->(f:FileEntity)-[r:CHANGED_IN]->(c:CommitEntity) WHERE f.path = {0} AND c.timestamp <= {2}  "
          + "AND c.timestamp > {1} AND ID(p) = {3} AND r.changeType = \"RENAME\""
          + " RETURN head(collect(DISTINCT r)).oldPath")
  @NonNull
  String findOldpathIfRenamedBetweenCommits(
      @NonNull String path,
      @NonNull Long commit1Time,
      @NonNull Long commit2Time,
      @NonNull Long projectId);

  @Query("MATCH (f:FileEntity) WHERE ID(f) IN {0} RETURN f")
  @NonNull
  List<FileEntity> findAllById(@NonNull List<Long> fileIds);
}
