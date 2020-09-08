package io.reflectoring.coderadar.graph.projectadministration.branch.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.BranchEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;

public interface BranchRepository extends Neo4jRepository<BranchEntity, Long> {

  /**
   * @param projectId The project id.
   * @return A list of the branches in the project sorted by name.
   */
  @Query("MATCH (p)-[:HAS_BRANCH]->(b) WHERE ID(p) = $0 RETURN b ORDER BY b.name")
  List<BranchEntity> getBranchesInProjectSortedByName(long projectId);

  /**
   * @param projectId The project id.
   * @return A list of the branches in the project.
   */
  @Query("MATCH (p)-[:HAS_BRANCH]->(b) WHERE ID(p) = $0 RETURN b")
  List<BranchEntity> getBranchesInProject(long projectId);

  /**
   * Create a new branch on the commit with the given hash.
   *
   * @param projectId The id of the project.
   * @param commitHash The commit hash to set the branch on.
   * @param branchName The name of the branch.
   */
  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE ID(p) = $0 AND c.hash = $1 WITH c, p LIMIT 1 "
          + "CREATE (b:BranchEntity {name: $2, commitHash: $1, isTag: $3})-[:POINTS_TO]->(c) WITH b, p "
          + "CREATE (p)-[:HAS_BRANCH]->(b)")
  void setBranchOnCommit(
      long projectId, @NonNull String commitHash, @NonNull String branchName, boolean isTag);

  /**
   * @param projectId The id of the project.
   * @param branchName The name of the branch
   * @return True if a branch with the name exists in the project.
   */
  @Query(
      "MATCH (p)-[:HAS_BRANCH]->(b) WHERE ID(p) = $0 AND b.name = $1 WITH b LIMIT 1 RETURN COUNT(b) > 0")
  boolean branchExistsInProject(long projectId, @NonNull String branchName);

  /**
   * Moves the head of branch to another commit.
   *
   * @param projectId The project id.
   * @param branchName The name of the branch.
   * @param commitHash The hash of the commit to move the branch to.
   */
  @Query(
      "MATCH (p)-[:HAS_BRANCH]->(b) WHERE ID(p) = $0 AND b.name = $1 WITH p, b LIMIT 1 "
          + "OPTIONAL MATCH (b)-[r:POINTS_TO]->() DELETE r WITH p, b "
          + "MATCH (p)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE c.hash = $2 WITH c, b LIMIT 1 "
          + "CREATE (b)-[r1:POINTS_TO]->(c) SET b.commitHash = c.hash")
  void moveBranchToCommit(long projectId, @NonNull String branchName, @NonNull String commitHash);

  @Query(
      "MATCH (p)-[:HAS_BRANCH]->(b) WHERE ID(p) = $0 "
          + "AND b.name = $1 WITH b LIMIT 1 "
          + "MATCH (b)-[:POINTS_TO]->(c) RETURN c LIMIT 1")
  CommitEntity getBranchCommit(long projectId, String branchName);

  @Query("MATCH (p)-[:HAS_BRANCH]->(b) WHERE ID(p) = $0 AND b.name = $1 RETURN b LIMIT 1")
  BranchEntity findBranchInProjectByName(long projectId, String name);
}
