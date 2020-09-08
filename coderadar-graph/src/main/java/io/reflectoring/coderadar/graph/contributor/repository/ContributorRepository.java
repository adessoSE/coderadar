package io.reflectoring.coderadar.graph.contributor.repository;

import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributorRepository extends Neo4jRepository<ContributorEntity, Long> {
  @Query("MATCH (c) WHERE ID(c) = $0 RETURN c")
  Optional<ContributorEntity> findById(long id);

  @Query("MATCH (c)-[r:WORKS_ON]->(p) WHERE ID(c) IN $0 RETURN c, r, p")
  List<ContributorEntity> findAllByIdsWithProjects(List<Long> ids);

  @Query("MATCH (co:ContributorEntity) WHERE SIZE((co)-[:WORKS_ON]->()) = 0 DELETE co")
  void deleteContributorsWithoutProjects();

  @Query("MATCH (c)-[:WORKS_ON]->(p) WHERE ID(p) = $0 RETURN c ORDER BY c.displayName")
  List<ContributorEntity> findAllByProjectId(long projectId);

  @Query("MATCH (c:ContributorEntity) RETURN c ORDER BY c.displayName")
  List<ContributorEntity> findAll();

  @Query("MATCH (c:ContributorEntity)-[r:WORKS_ON]->(p) WHERE ID(p) = $1 AND ID(c) = $0 DELETE r")
  void detachContributorFromProject(long contributorId, long projectId);

  @Query("MATCH (c:ContributorEntity)-[r:WORKS_ON]->(p) WHERE ID(p) = $1 AND ID(c) IN $0 DELETE r")
  void detachContributorsFromProject(List<Long> contributorIds, long projectId);
}
