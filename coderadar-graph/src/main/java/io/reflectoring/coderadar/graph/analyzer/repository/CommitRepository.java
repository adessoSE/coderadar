package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.analyzer.domain.CommitEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface CommitRepository extends Neo4jRepository<CommitEntity, Long> {
  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS*]-(f:FileEntity)-->(c1:CommitEntity)-[:IS_CHILD_OF*0..2]-(c2:CommitEntity) "
          + "WHERE ID(p) = {0} UNWIND [c1, c2] AS c WITH DISTINCT c SET c.analyzed = false")
  void resetAnalyzedStatus(Long projectId);

  @Query(
      "MATCH (c1:CommitEntity), (c1)-[r:IS_CHILD_OF]->(c2) WHERE ID(c1) = {commitId} DETACH DELETE c1, c2")
  void deleteCommitTree(@Param("commitId") Long commitId);

  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS*]-(f:FileEntity)-->(c1:CommitEntity)-[:IS_CHILD_OF*0..2]-(c2:CommitEntity) "
          + "WHERE ID(p) = {0} "
          + "UNWIND [c1, c2] AS c "
          + "RETURN DISTINCT c "
          + "ORDER BY c.timestamp DESC")
  List<CommitEntity> findByProjectId(Long projectId);

  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS*]-(:FileEntity)-[:CHANGED_IN]->(c:CommitEntity) WHERE c.name = {0} AND ID(p) = {1} RETURN c")
  Optional<CommitEntity> findByNameAndProjectId(String commit, Long projectId);

  @Query("MATCH (c:CommitEntity) WHERE c IN {0} DETACH DELETE c")
  void deleteCommits(List<CommitEntity> commitEntities);
}
