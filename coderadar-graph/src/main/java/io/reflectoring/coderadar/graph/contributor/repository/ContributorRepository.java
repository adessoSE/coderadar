package io.reflectoring.coderadar.graph.contributor.repository;

import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributorRepository extends Neo4jRepository<ContributorEntity, Long> {
  @Query("MATCH (c) WHERE ID(c) = {0} RETURN c")
  Optional<ContributorEntity> findById(long id);

  @Query("MATCH (c)-[:WORKS_ON]->(p) WHERE ID(p) = {0} RETURN c ORDER BY c.displayName")
  List<ContributorEntity> findAllByProjectId(long projectId);

  @Query(
      "MATCH (c)-[:WORKS_ON]->(p)-[:CONTAINS_COMMIT]->(co)<-[:CHANGED_IN]-(f)"
          + " WHERE ID(p) = {0} AND f.path ENDS WITH {1} AND co.author IN c.names RETURN c ORDER BY c.displayName")
  List<ContributorEntity> findAllByProjectIdAndFilename(long projectId, String fileName);

  @Query("MATCH (c:ContributorEntity) RETURN c ORDER BY c.displayName")
  List<ContributorEntity> findAll();
}
