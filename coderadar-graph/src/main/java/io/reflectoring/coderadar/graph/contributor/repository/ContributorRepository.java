package io.reflectoring.coderadar.graph.contributor.repository;

import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import java.util.List;
import java.util.Optional;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributorRepository extends Neo4jRepository<ContributorEntity, Long> {
  @Query("MATCH (c) WHERE ID(c) = {0} RETURN c")
  @NonNull
  Optional<ContributorEntity> findById(@NonNull Long id);

  @Query("MATCH (c:ContributorEntity)-[:WORKS_ON]->(p:ProjectEntity) WHERE ID(p) = {0} RETURN c")
  List<ContributorEntity> findAllByProjectId(Long projectId);

  @Query(
      "MATCH (c:ContributorEntity)-[:WORKS_ON]->(p:ProjectEntity)-[:CONTAINS_COMMIT]->(co:CommitEntity)<-[:CHANGED_IN]-(f:FileEntity)"
          + " WHERE ID(p) = {0} AND f.path ENDS WITH {1} AND co.author IN c.names RETURN c")
  List<ContributorEntity> findAllByProjectIdAndFilename(Long projectId, String fileName);
}
