package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.FileEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;

public interface FileRepository extends Neo4jRepository<FileEntity, Long> {

  /**
   * @param projectId The project id.
   * @return All of the files in a project (including those part of modules).
   */
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

  /**
   * Checks if files matching the given paths have been renamed between two points in time.
   *
   * @param paths The paths to check.
   * @param commit1Time The first (older) commit timestamp.
   * @param commit2Time The second (newer) commit timestamp.
   * @param projectId The project id.
   * @return A list of maps that contain two values in the following format: {"oldPath":
   *     "/src/main/File.java", "newPath": "File.java"}.
   */
  @Query(
      "MATCH (p)-[:CONTAINS*]->(f:FileEntity)-[r:CHANGED_IN]->(c:CommitEntity) WHERE f.path IN {0} AND c.timestamp <= {2}  "
          + "AND c.timestamp > {1} AND ID(p) = {3} AND r.changeType = \"RENAME\" "
          + "RETURN {oldPath: head(collect(DISTINCT r)).oldPath, newPath: f.path} as rename")
  @NonNull
  List<Map<String, Object>> findOldPathsIfRenamedBetweenCommits(
      @NonNull List<String> paths,
      @NonNull Long commit1Time,
      @NonNull Long commit2Time,
      @NonNull Long projectId);

  /**
   * Creates [:RENAMED_FROM] relationships between files.
   *
   * @param renameRels A list of maps, each containing two file ids. The file being renamed
   *     ("fileId1") and the file it being renamed from ("fileId2").
   */
  @Query(
      "UNWIND {0} as x "
          + "MATCH (f1) WHERE ID(f1) = x.fileId1 "
          + "MATCH (f2) WHERE ID(f2) = x.fileId2 "
          + "CREATE (f1)-[:RENAMED_FROM]->(f2)")
  void createRenameRelationships(List<HashMap<String, Object>> renameRels);
}
