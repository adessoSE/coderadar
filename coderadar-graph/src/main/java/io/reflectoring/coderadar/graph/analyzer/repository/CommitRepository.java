package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface CommitRepository extends Neo4jRepository<CommitEntity, Long> {
  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE ID(p) = {0} SET c.analyzed = false")
  void resetAnalyzedStatus(@NonNull Long projectId);

  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE ID(p) = {0} RETURN c ORDER BY c.timestamp DESC")
  @NonNull
  List<CommitEntity> findByProjectIdAndTimestampDesc(@NonNull Long projectId);

  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE ID(p) = {0} WITH c "
          + "OPTIONAL MATCH (c)-[r2:IS_CHILD_OF]-(c1) "
          + "OPTIONAL MATCH (c)<-[r:CHANGED_IN]-(f:FileEntity) RETURN DISTINCT c, c1, r, r2, f ORDER BY c.timestamp")
  @NonNull
  List<CommitEntity> findByProjectIdWithAllRelationshipsSortedByTimestampAsc(
      @NonNull Long projectId);

  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE ID(p) = {0} WITH c "
          + "OPTIONAL MATCH (c)<-[r:CHANGED_IN]-(f:FileEntity) RETURN DISTINCT c, r, f ORDER BY c.timestamp")
  @NonNull
  List<CommitEntity> findByProjectIdWithFileRelationshipsSortedByTimestampAsc(
      @NonNull Long projectId);

  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE ID(p) = {0} AND c.analyzed = FALSE WITH c "
          + "OPTIONAL MATCH (c)<-[r:CHANGED_IN]-(f:FileEntity) WHERE any(x IN {1} WHERE f.path =~ x) "
          + "AND none(x IN {2} WHERE f.path =~ x) "
          + "RETURN DISTINCT c, r, f ORDER BY c.timestamp")
  @NonNull
  List<CommitEntity> findByProjectIdNonanalyzedWithFileRelationshipsSortedByTimestampAsc(
      @NonNull Long projectId, @NonNull List<String> includes, List<String> excludes);

  @Query("MATCH (p:ProjectEntity)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE ID(p) = {0} RETURN c")
  @NonNull
  List<CommitEntity> findByProjectId(@NonNull Long projectId);

  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE ID(p) = {0} RETURN c ORDER BY c.timestamp DESC LIMIT 1")
  @NonNull
  CommitEntity findHeadCommit(@NonNull Long projectId);

  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE c.name = {0} AND ID(p) = {1} RETURN c")
  @NonNull
  Optional<CommitEntity> findByNameAndProjectId(@NonNull String commit, @NonNull Long projectId);

  @Query("MATCH (c:CommitEntity) WHERE ID(c) IN {0} SET c.analyzed = true")
  void setCommitsWithIDsAsAnalyzed(@NonNull long[] commitIds);

  @Query("MATCH (c:CommitEntity) WHERE ID(c) IN {0} RETURN c")
  @NonNull
  List<CommitEntity> findAllById(@NonNull List<Long> commitIds);
}
