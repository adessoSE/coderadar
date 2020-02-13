package io.reflectoring.coderadar.graph.contributor.repository;

import io.reflectoring.coderadar.graph.contributor.domain.ContributorEntity;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributorRepository extends Neo4jRepository<ContributorEntity, Long> {
  @Query("MATCH (c:ContributorEntity)-[:WORKS_ON]->(p:ProjectEntity) WHERE ID(p) = {0} RETURN c")
  List<ContributorEntity> findAllByProjectId(Long projectId);
}
