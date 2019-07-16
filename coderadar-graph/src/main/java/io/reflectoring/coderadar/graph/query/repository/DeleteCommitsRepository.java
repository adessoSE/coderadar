package io.reflectoring.coderadar.graph.query.repository;

import io.reflectoring.coderadar.graph.analyzer.domain.CommitEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeleteCommitsRepository extends Neo4jRepository<CommitEntity, Long> {

  @Query("MATCH (c1:CommitEntity), (c1)-[r:IS_CHILD_OF]->(c2) WHERE ID(c1) = {commitId} DETACH DELETE c1, c2")
  void deleteCommitTree(@Param("commitId") Long commitId);
}
