package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;

public interface CommitRepository extends Neo4jRepository<CommitEntity, Long> {

  /**
   * Sets the analyzed flag on all commit entities in a project to false.
   *
   * @param projectId The project id.
   */
  @Query("MATCH (p)-[:CONTAINS_COMMIT]->(c) WHERE ID(p) = {0} SET c.analyzed = false")
  void resetAnalyzedStatus(@NonNull Long projectId);

  /**
   * Returns all commits in a project with FileToCommitRelationships and parent relationships where
   * the file paths match the given include and exclude regular expressions and where the change
   * type is not "DELETED". NOTE: uses APOC
   *
   * @param projectId The id of the project.
   * @param includes The paths to include (regex)
   * @param excludes The paths to exclude (regex)
   * @return A list of commit entities with initialized FileToCommitRelationships.
   */
  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c)<-[:POINTS_TO]-(b) WHERE ID(p) = {0} AND b.name = {1} WITH c "
          + "CALL apoc.path.subgraphNodes(c, {relationshipFilter:'IS_CHILD_OF>'}) YIELD node WITH node ORDER BY node.timestamp ASC "
          + "OPTIONAL MATCH (node)<-[r:CHANGED_IN]-(f:FileEntity) WHERE r.changeType <> \"DELETE\" AND any(x IN {2} WHERE f.path =~ x) "
          + "AND none(x IN {3} WHERE f.path =~ x) RETURN DISTINCT node, r, f")
  @NonNull
  List<CommitEntity> findByProjectIdNonAnalyzedWithFileAndParentRelationships(
      @NonNull Long projectId,
      @NonNull String branchName,
      @NonNull List<String> includes,
      List<String> excludes);

  /**
   * Returns all commits in a project. (FileToCommitRelationships and parents are not initialized).
   *
   * @param projectId The project id.
   * @return A list of commits in the project.
   */
  @Query("MATCH (p)-[:CONTAINS_COMMIT]->(c) WHERE ID(p) = {0} RETURN c")
  @NonNull
  List<CommitEntity> findByProjectId(@NonNull Long projectId);

  /**
   * Sets all of the commits with the given id to analyzed.
   *
   * @param commitIds The commit ids.
   */
  @Query("MATCH (c:CommitEntity) WHERE ID(c) IN {0} SET c.analyzed = true")
  void setCommitsWithIDsAsAnalyzed(@NonNull long[] commitIds);

  /**
   * @param commitHash The commit hash.
   * @param projectId The project id.
   * @return The timestamp of the commit given its hash value.
   */
  @Query("MATCH (p)-[:CONTAINS_COMMIT]->(c) WHERE c.name = {0} AND ID(p) = {1} RETURN c.timestamp")
  @NonNull
  Optional<Long> findTimestampByNameAndProjectId(String commitHash, Long projectId);

  /**
   * Creates [:IS_CHILD_OF] Relationships between commits.
   *
   * @param parentRels A list of maps, each containing the ids of the child (id1) and parent (id2)
   *     commit.
   */
  @Query(
      "UNWIND {0} as c "
          + "MATCH (c1) WHERE ID(c1) = c.id1 "
          + "MATCH (c2) WHERE ID(c2) = c.id2 "
          + "CREATE (c1)-[:IS_CHILD_OF]->(c2)")
  void createParentRelationships(List<HashMap<String, Object>> parentRels);

  /**
   * Creates [:CHANGED_IN] Relationships between files and commits.
   *
   * @param fileRels A list of maps, each containing the ids of the commit (commitId) and file
   *     (fileId) as well as the change type (changeType) and old file path (oldPath).
   */
  @Query(
      "UNWIND {0} as x "
          + "MATCH (c) WHERE ID(c) = x.commitId "
          + "MATCH (f) WHERE ID(f) = x.fileId "
          + "CREATE (f)-[:CHANGED_IN {changeType: x.changeType, oldPath: x.oldPath}]->(c)")
  void createFileRelationships(List<HashMap<String, Object>> fileRels);

  /**
   * NOTE: uses APOC
   *
   * @param projectId The project id.
   * @param branch The branch name.
   * @return All commits in the given project for the given branch.
   */
  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c)<-[:POINTS_TO]-(b) WHERE ID(p) = {0} AND b.name = {1} WITH c "
          + "CALL apoc.path.subgraphNodes(c, {relationshipFilter:'IS_CHILD_OF>'}) YIELD node "
          + "RETURN node ORDER BY node.timestamp DESC")
  List<CommitEntity> findByProjectIdAndBranchName(Long projectId, String branch);
}
