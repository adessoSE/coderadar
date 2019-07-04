package io.reflectoring.coderadar.graph.query.repository;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import java.util.List;

import io.reflectoring.coderadar.graph.analyzer.domain.CommitEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GetCommitsInProjectRepository extends Neo4jRepository<CommitEntity, Long> {

  @Query("MATCH (p:ProjectEntity)-[:CONTAINS]->(f:FileEntity)-[:CHANGED_IN]->(c:CommitEntity) WHERE ID(p) = {0} RETURN c " +
          "UNION MATCH (p:ProjectEntity)-[:CONTAINS]->(m:ModuleEntity)-[:CONTAINS]->(f:FileEntity)-[:CHANGED_IN]->(c:CommitEntity) WHERE ID(p) = {0} RETURN c")
  List<CommitEntity> findByProjectId(Long projectId);

  @Query(
    value =
        "MATCH (p:Project)-[:HAS]->(c:Commit) WHERE ID(p) = {0} RETURN c ORDER BY c.sequenceNumber DESC LIMIT 1"
  )
  Commit findTop1ByProjectIdOrderBySequenceNumberDesc(Long id);
}
