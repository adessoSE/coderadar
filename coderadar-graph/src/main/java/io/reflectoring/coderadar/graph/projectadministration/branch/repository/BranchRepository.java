package io.reflectoring.coderadar.graph.projectadministration.branch.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.BranchEntity;
import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface BranchRepository extends Neo4jRepository<BranchEntity, Long> {

  @Query("MATCH (p)-[:CONTAINS_COMMIT]->(c)<-[r:POINTS_TO]-(b) WHERE ID(p) = {0} RETURN b, r, c")
  List<BranchEntity> getBranchesInProject(long projectId);

  @Query(
          "MATCH (p)-[:CONTAINS_COMMIT]->(c)<-[r:POINTS_TO]-(b) WHERE ID(p) = {0} AND b.name = {1} RETURN c LIMIT 1")
  CommitEntity getCommitForBranch(Long projectId, String branch);

  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c) WHERE ID(p) = {0} AND c.name = {1} "
          + "CREATE (b:BranchEntity {name: {2} })-[:POINTS_TO]->(c) ")
  void setBranchOnCommit(Long projectId, String commitHash, String branchName);

  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c)<-[r:POINTS_TO]-(b) WHERE ID(p) = {0} AND b.name = {1} RETURN b, r, c LIMIT 1")
  BranchEntity findBranchInProjectByName(Long projectId, String branch);
}
