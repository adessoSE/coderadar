package io.reflectoring.coderadar.graph.query.repository;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.graph.analyzer.domain.CommitEntity;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GetCommitsInProjectRepository extends Neo4jRepository<CommitEntity, Long> {

  @Query("MATCH (p1:ProjectEntity)-[:CONTAINS*]-(f1:FileEntity)-[:CHANGED_IN]->(c1:CommitEntity) " +
          "OPTIONAL MATCH (c2:CommitEntity)-[:IS_CHILD_OF]->(c1:CommitEntity) " +
          "WHERE ID(p1) = {0} " +
          "UNWIND [c1, c2] AS c " +
          "RETURN DISTINCT c " +
          "ORDER BY c.timestamp DESC")
  List<CommitEntity> findByProjectId(Long projectId);

  @Query(
    value =
        "MATCH (p:Project)-[:HAS]->(c:Commit) WHERE ID(p) = {0} RETURN c ORDER BY c.sequenceNumber DESC LIMIT 1"
  )
  Commit findTop1ByProjectIdOrderBySequenceNumberDesc(Long id);
}
