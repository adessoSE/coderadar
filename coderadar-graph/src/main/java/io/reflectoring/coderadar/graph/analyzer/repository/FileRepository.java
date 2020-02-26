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
  @Query("MATCH (p)-[:CONTAINS*]->(f:FileEntity) WHERE ID(p) = {0} RETURN f")
  @NonNull
  List<FileEntity> findAllinProject(@NonNull Long projectId);

  /**
   * @param projectId The project id
   * @param commit1Hash The hash of the first commit
   * @param commit2Hash The hash of the second commit
   */
  @Query(
      "MATCH (c2)<-[:CONTAINS_COMMIT]-(p)-[:CONTAINS_COMMIT]->(c) WHERE ID(p) = {0} AND c.name = {2} "
          + " AND c2.name = {1} WITH c, c2 LIMIT 1 "
          + "CALL apoc.path.spanningTree(c, {relationshipFilter:'IS_CHILD_OF>', terminatorNodes: [c2]}) "
          + "YIELD path WITH nodes(path) as commits, c2 UNWIND commits as c WITH c WHERE c <> c2 WITH collect(c) as commits "
          + "CALL apoc.cypher.run('UNWIND commits as c MATCH (c)<-[:CHANGED_IN {changeType: \"DELETE\"}]-(f) RETURN collect(f) as deletes', {commits: commits}) YIELD value WITH value.deletes as deletes, commits "
          + "CALL apoc.cypher.run('UNWIND commits as c OPTIONAL MATCH (f)<-[:RENAMED_FROM]-()-[:CHANGED_IN {changeType: \"RENAME\"}]->(c) RETURN collect(f) as renames', {commits: commits}) "
          + "YIELD value WITH value.renames as renames, commits, deletes "
          + "UNWIND commits as c "
          + "MATCH (c)<-[:CHANGED_IN {changeType: \"MODIFY\"}]-(f) WHERE NOT (f IN deletes OR f IN renames) "
          + "RETURN DISTINCT f.path ORDER BY f.path")
  @NonNull
  List<String> getFilesModifiedBetweenCommits(
      @NonNull Long projectId, @NonNull String commit1Hash, @NonNull String commit2Hash);

  /**
   * Checks if files matching the given paths have been renamed between two points in time.
   *
   * @param paths The paths to check.
   * @param commit1Hash The first (older) commit hash.
   * @param commit2hash The second (newer) commit hash.
   * @param projectId The project id.
   * @return A list of maps that contain two values in the following format: {"oldPath":
   *     "/src/main/File.java", "newPath": "File.java"}.
   */
  @Query(
      "MATCH (c2)<-[:CONTAINS_COMMIT]-(p)-[:CONTAINS_COMMIT]->(c) WHERE ID(p) = {0} AND c.name = {3} AND c2.name = {2} WITH c, c2 LIMIT 1 "
          + "CALL apoc.path.spanningTree(c, {relationshipFilter:'IS_CHILD_OF>', terminatorNodes: [c2]}) "
          + "YIELD path WITH nodes(path) as commits, c2 UNWIND commits as c WITH c WHERE c <> c2 WITH collect(c) as commits "
          + "CALL apoc.cypher.run('UNWIND commits as c MATCH (c)<-[:CHANGED_IN {changeType: \"DELETE\"}]-(f) RETURN collect(f) as deletes', {commits: commits}) "
          + "YIELD value WITH value.deletes as deletes, commits "
          + "UNWIND commits as c "
          + "MATCH (c)<-[r:CHANGED_IN {changeType: \"RENAME\"}]-(f) WHERE NOT f IN deletes AND f.path IN {1} "
          + "RETURN {oldPath: head(collect(DISTINCT r)).oldPath, newPath: f.path} as rename")
  @NonNull
  List<Map<String, Object>> findOldPathsIfRenamedBetweenCommits(
      @NonNull Long projectId,
      @NonNull List<String> paths,
      @NonNull String commit1Hash,
      @NonNull String commit2hash);

  /**
   * Creates [:RENAMED_FROM] relationships between files.
   *
   * @param renameRels A list of maps, each containing two file ids. The file being renamed
   *     ("fileId1") and the file its being renamed from ("fileId2").
   */
  @Query(
      "UNWIND {0} as x "
          + "MATCH (f1) WHERE ID(f1) = x.fileId1 "
          + "MATCH (f2) WHERE ID(f2) = x.fileId2 "
          + "CREATE (f1)-[:RENAMED_FROM]->(f2)")
  void createRenameRelationships(@NonNull List<HashMap<String, Object>> renameRels);

  /**
   * @param projectId The project id.
   * @return The file with the given sequence id or null if it does not exist.
   */
  @Query(
      "MATCH (p)-[:CONTAINS*]->(f:FileEntity) WHERE ID(p) = {0} AND f.sequenceId = {1} RETURN f LIMIT 1 ")
  FileEntity getFileInProjectBySequenceId(@NonNull Long projectId, @NonNull Long sequenceId);
}
