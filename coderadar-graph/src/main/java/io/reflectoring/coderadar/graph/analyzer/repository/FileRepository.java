package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.FileEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;

public interface FileRepository extends Neo4jRepository<FileEntity, Long> {

  @Query("MATCH (p)-[:CONTAINS*]->(f) WHERE ID(p) = {0} RETURN f")
  @NonNull
  List<FileEntity> findAllinProject(@NonNull Long projectId);

  /**
   * @param commit1Time The time of the first commit
   * @param commit2Time The time of the second commit
   * @param projectId The project id
   * @return The paths of the files that have been modified
   */
  @Query(
      "MATCH (p)-[:CONTAINS*]->(f:FileEntity)-[r:CHANGED_IN {changeType: \"MODIFY\"}]->(c:CommitEntity) WHERE ID(p) = {2} "
          + "AND c.timestamp <= {1} AND c.timestamp > {0} "
          + "RETURN DISTINCT f.path")
  @NonNull
  List<String> getFilesModifiedBetweenCommits(
      @NonNull Long commit1Time, @NonNull Long commit2Time, @NonNull Long projectId);

  @Query(
      "MATCH (p)-[:CONTAINS*]->(f:FileEntity)-[r:CHANGED_IN {changeType: \"RENAME\"}]->(c:CommitEntity) WHERE ID(p) = {2} "
          + "AND c.timestamp <= {1} AND c.timestamp > {0} "
          + "RETURN collect(DISTINCT r.oldPath) as paths")
  @NonNull
  List<String> getFilesRenamedBetweenCommits(
      @NonNull Long commit1Time, @NonNull Long commit2Time, @NonNull Long projectId);

  @Query(
      "MATCH (p)-[:CONTAINS*]->(f:FileEntity)-[r:CHANGED_IN]->(c:CommitEntity) WHERE f.path IN {0} AND c.timestamp <= {2}  "
          + "AND c.timestamp > {1} AND ID(p) = {3} AND r.changeType = \"RENAME\" "
          + "RETURN {oldPath: head(collect(DISTINCT r)).oldPath, newPath: f.path} as rename")
  @NonNull
  List<Map<String, Object>> findOldpathIfRenamedBetweenCommits(
      @NonNull List<String> path,
      @NonNull Long commit1Time,
      @NonNull Long commit2Time,
      @NonNull Long projectId);

  @Query("MATCH (f) WHERE ID(f) IN {0} RETURN f")
  @NonNull
  List<FileEntity> findAllById(@NonNull List<Long> fileIds);

  @Query(
      "UNWIND {0} as x "
          + "MATCH (f1) WHERE ID(f1) = x.fileId1 "
          + "MATCH (f2) WHERE ID(f2) = x.fileId2 "
          + "CREATE (f1)-[:RENAMED_FROM]->(f2)")
  void createRenameRelationships(List<HashMap<String, Object>> renameRels);

  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(co)<-[:CHANGED_IN {changeType: \"DELETE\"}]-(f) WHERE ID(p) = {0} WITH collect(DISTINCT f) AS deletes "
          + "MATCH (c)-[:WORKS_ON]->(p)-[:CONTAINS*]->(f)-[:CHANGED_IN]->(co) WHERE ID(p) = {0} AND f.path ENDS WITH \".java\" "
          + "AND NOT f IN deletes AND co.author IN c.names AND NOT ()-[:RENAMED_FROM]->(f) "
          + "WITH f.path as path, collect(DISTINCT c) AS contributors "
          + "WHERE size(contributors) = 1 RETURN path")
  List<String> getCriticalFiles(@NonNull Long projectId);
}
