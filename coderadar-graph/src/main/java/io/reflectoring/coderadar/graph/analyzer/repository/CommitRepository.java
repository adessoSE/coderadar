package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.analyzer.domain.CommitEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CommitRepository extends Neo4jRepository<CommitEntity, Long> {
  @Query(
      "MATCH (p:ProjectEntity)-->(c:CommitEntity) WHERE ID(p) = {0} WITH c SET c.analyzed = false")
  void resetAnalyzedStatus(Long projectId);

  @Query(
      "MATCH (p:ProjectEntity)-->(c:CommitEntity) WHERE ID(p) = {0} RETURN c ORDER BY c.timestamp DESC")
  List<CommitEntity> findByProjectIdAndTimestampDesc(Long projectId);

  @Query("MATCH (p:ProjectEntity)-->(c:CommitEntity) WHERE ID(p) = {0} RETURN c")
  List<CommitEntity> findByProjectId(Long projectId);

  @Query("MATCH (p:ProjectEntity)-->(c:CommitEntity) WHERE c.name = {0} AND ID(p) = {1} RETURN c")
  Optional<CommitEntity> findByNameAndProjectId(String commit, Long projectId);

  @Query("MATCH (c:CommitEntity) WHERE c IN {0} DETACH DELETE c")
  void deleteCommits(List<CommitEntity> commitEntities);
}
