package io.reflectoring.coderadar.graph.query.repository;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GetCommitsInProjectRepository extends Neo4jRepository<Commit, Long> {

  @Query("MATCH (p:Project)-[:HAS]->(c:Commit) WHERE ID(p) = {0} RETURN c")
  List<Commit> findByProjectId(Long projectId);

  @Query(
    value =
        "MATCH (p:Project)-[:HAS]->(c:Commit) WHERE ID(p) = {0} RETURN c ORDER BY c.sequenceNumber DESC LIMIT 1"
  )
  Commit findTop1ByProjectIdOrderBySequenceNumberDesc(Long id);
}
