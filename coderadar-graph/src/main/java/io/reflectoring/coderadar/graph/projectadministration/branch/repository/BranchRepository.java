package io.reflectoring.coderadar.graph.projectadministration.branch.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.BranchEntity;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface BranchRepository extends Neo4jRepository<BranchEntity, Long> {

  @Query("MATCH (p)-[:CONTAINS_COMMIT]->()<-[:POINTS_TO]-(b) WHERE ID(p) = {0} RETURN b")
  List<BranchEntity> getBranchesInProject(long projectId);
}
