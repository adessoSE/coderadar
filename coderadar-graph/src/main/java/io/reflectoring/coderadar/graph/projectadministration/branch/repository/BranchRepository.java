package io.reflectoring.coderadar.graph.projectadministration.branch.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.BranchEntity;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface BranchRepository extends Neo4jRepository<BranchEntity, Long> {

  /**
   * @param projectId The project id.
   * @return A list of the branches in the project (with initialized [:POINTS_TO] relationships).
   */
  @Query("MATCH (p)-[:CONTAINS_COMMIT]->(c)<-[r:POINTS_TO]-(b) WHERE ID(p) = {0} RETURN b, r, c")
  List<BranchEntity> getBranchesInProject(long projectId);

  /**
   * Create a new branch on the commit with the given hash.
   *
   * @param projectId The id of the project.
   * @param commitHash The commit hash to set the branch on.
   * @param branchName The name of the branch.
   */
  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c) WHERE ID(p) = {0} AND c.name = {1} "
          + "CREATE (b:BranchEntity {name: {2} })-[:POINTS_TO]->(c) ")
  void setBranchOnCommit(Long projectId, String commitHash, String branchName);

  /**
   * @param projectId The id of the project.
   * @param branchName The name of the branch
   * @return True if a branch with the name exists in the project.
   */
  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c)<-[r:POINTS_TO]-(b) WHERE ID(p) = {0} AND b.name = {1} RETURN COUNT(b) > 0")
  boolean branchExistsInProject(Long projectId, String branchName);

  /**
   * Moves the head of branch to another commit.
   *
   * @param projectId The project id.
   * @param branchName The name of the branch.
   * @param commitHash The hash of the commit to move the branch to.
   */
  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->()<-[r:POINTS_TO]-(b)  WHERE ID(p) = {0} AND b.name = {1} DELETE r WITH p, b"
          + " MATCH (p)-[:CONTAINS_COMMIT]->(c) WHERE c.name = {2} CREATE (b)-[r1:POINTS_TO]->(c)")
  void moveBranchToCommit(Long projectId, String branchName, String commitHash);
}