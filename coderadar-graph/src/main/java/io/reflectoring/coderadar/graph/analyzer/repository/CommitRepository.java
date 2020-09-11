package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
  void resetAnalyzedStatus(long projectId);

  /**
   * Returns all commits in a project with FileToCommitRelationships and parent relationships where
   * the file paths match the given include and exclude regular expressions and where the change
   * type is not "DELETED". NOTE: uses APOC
   *
   * @param projectId The id of the project.
   * @param includes The paths to include (regex)
   * @param excludes The paths to exclude (regex)A
   * @return A list of commit entities with initialized changedFiles except deletes.
   */
  @Query(
      "MATCH (p)-[:HAS_BRANCH]->(b:BranchEntity)-[:POINTS_TO]->(c) WHERE ID(p) = {0} AND b.name = {1} WITH c LIMIT 1 "
          + "CALL apoc.path.subgraphNodes(c, {relationshipFilter:'IS_CHILD_OF>'}) YIELD node WITH node as c WHERE NOT c.analyzed "
          + "OPTIONAL MATCH (c)<-[:CHANGED_IN]-(f) WHERE any(x IN {2} WHERE f.path =~ x) "
          + "AND none(x IN {3} WHERE f.path =~ x) WITH c, collect({path: f.path, id: ID(f)}) as files ORDER BY c.timestamp ASC "
          + "RETURN {id: ID(c), hash: c.hash} as commit, files")
  @NonNull
  List<Map<String, Object>> findByProjectIdNonAnalyzedWithFiles(
      long projectId,
      @NonNull String branchName,
      @NonNull List<String> includes,
      @NonNull List<String> excludes);

  /**
   * NOTE: uses APOC
   *
   * @param projectId The project id.
   * @param branch The branch name.
   * @return All commits in the given project for the given branch.
   */
  @Query(
      "MATCH (p)-[:HAS_BRANCH]->(b:BranchEntity)-[:POINTS_TO]->(c) WHERE ID(p) = {0} AND b.name = {1} WITH c LIMIT 1 "
          + "CALL apoc.path.subgraphNodes(c, {relationshipFilter:'IS_CHILD_OF>'}) YIELD node "
          + "RETURN node ORDER BY node.timestamp DESC")
  List<CommitEntity> findByProjectIdAndBranchName(long projectId, String branch);

  /**
   * Returns all commits in a project. (Files and parents are not initialized).
   *
   * @param projectId The project id.
   * @return A list of commits in the project.
   */
  @Query("MATCH (p)-[:CONTAINS_COMMIT]->(c) WHERE ID(p) = {0} RETURN c")
  @NonNull
  List<CommitEntity> findByProjectId(long projectId);

  /**
   * Returns all commits in a project. (Parents are not initialized).
   *
   * @param projectId The project id.
   * @return A list of commits in the project.
   */
  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c) WHERE ID(p) = {0} WITH c "
          + "OPTIONAL MATCH (f)-[:CHANGED_IN]->(c) WITH c, f "
          + "OPTIONAL MATCH (f2)-[:DELETED_IN]->(c) RETURN c, f, f2")
  @NonNull
  List<CommitEntity> findByProjectIdWithFiles(long projectId);

  /**
   * Returns all commits in a project with hashes of their parents.
   *
   * @param projectId The project id.
   * @return A list of commits in the project.
   */
  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c) WHERE ID(p) = {0} WITH c "
          + "OPTIONAL MATCH (c)-[r:IS_CHILD_OF]->(c1) "
          + "WITH c as commit, c1.hash as parent ORDER BY r.parentOrder "
          + "RETURN commit, collect(parent) as parents "
          + "ORDER BY commit.timestamp DESC")
  @NonNull
  List<LinkedHashMap<String, Object>> findByProjectIdWithParents(long projectId);

  /**
   * Sets all of the commits with the given id to analyzed.
   *
   * @param commitIds The commit ids.
   */
  @Query("MATCH (c) WHERE ID(c) IN {0} SET c.analyzed = true")
  void setCommitsWithIDsAsAnalyzed(@NonNull List<Long> commitIds);

  /**
   * Creates [:IS_CHILD_OF] Relationships between commits.
   *
   * @param parentRels A list of maps, each containing the ids of the child (id1) and parent (id2)
   *     commit.
   */
  @Query(
      "UNWIND {0} as c "
          + "MATCH (c1) WHERE ID(c1) = c[0] "
          + "MATCH (c2) WHERE ID(c2) = c[1] "
          + "CREATE (c1)-[:IS_CHILD_OF {parentOrder: c[2]}]->(c2)")
  void createParentRelationships(List<Long[]> parentRels);

  /**
   * Creates [:CHANGED_IN] Relationships between files and commits.
   *
   * @param fileRels A list of maps, each containing the ids of the commit (commitId) and file
   *     (fileId) as well as the change type (changeType).
   */
  @Query(
      "UNWIND {0} as x "
          + "MATCH (c) WHERE ID(c) = x[0] "
          + "MATCH (f) WHERE ID(f) = x[1] "
          + "CREATE (f)-[:CHANGED_IN]->(c)")
  void createFileRelationships(List<long[]> fileRels);

  @Query(
      "UNWIND {0} as x "
          + "MATCH (c) WHERE ID(c) = x[0] "
          + "MATCH (f) WHERE ID(f) = x[1] "
          + "CREATE (f)-[:DELETED_IN]->(c)")
  void createFileDeleteRelationships(List<long[]> fileRels);

  /**
   * @param commit1 The full hash of the first commit.
   * @param commit2 The full hash of the second commit.
   * @return True if commit1 was made after commit 2, false otherwise
   */
  @Query(
      "MATCH (c1:CommitEntity) WHERE c1.hash = {0} WITH c1 LIMIT 1 "
          + "MATCH (c2:CommitEntity) WHERE c2.hash = {1} WITH c1, c2 LIMIT 1 "
          + "RETURN c1.timestamp > c2.timestamp")
  boolean commitIsNewer(@NonNull String commit1, @NonNull String commit2);

  @Query("MATCH (c)-[:IS_CHILD_OF]->(c2) WHERE ID(c) = {0} RETURN c2")
  List<CommitEntity> getCommitParents(long commitId);

  @Query("MATCH (c2)-[:IS_CHILD_OF]->(c) WHERE ID(c) = {0} RETURN c2")
  List<CommitEntity> getCommitChildren(long commitId);

  @Query(
      "MATCH (c) WHERE ID(c) = {0} WITH c "
          + "OPTIONAL MATCH (f)-[r:CHANGED_IN]->(c) "
          + "WHERE NOT EXISTS((c)--(f)-[:CHANGED_IN]->()) DETACH DELETE c, f")
  void deleteCommitAndAddedOrRenamedFiles(long commitId);

  @Query(
      "MATCH (co)-[:WORKS_ON]->(p)-[:HAS_BRANCH]->(b:BranchEntity)-[:POINTS_TO]->(c) WHERE toLower({2}) IN co.emails "
          + "AND ID(p) = {0} AND b.name = {1} WITH co.emails as emails, c LIMIT 1 "
          + "CALL apoc.path.subgraphNodes(c, {relationshipFilter:'IS_CHILD_OF>'}) YIELD node WITH node as c "
          + "WHERE toLower(c.authorEmail) IN emails "
          + "RETURN c ORDER BY c.timestamp DESC")
  List<CommitEntity> findByProjectIdBranchNameAndContributor(
      long projectId, String branchName, String email);
}
