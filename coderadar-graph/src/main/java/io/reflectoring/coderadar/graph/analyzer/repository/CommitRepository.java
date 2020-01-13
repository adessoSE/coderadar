package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.CommitEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;

public interface CommitRepository extends Neo4jRepository<CommitEntity, Long> {
  @Query("MATCH (p)-[:CONTAINS_COMMIT]->(c) WHERE ID(p) = {0} SET c.analyzed = false")
  void resetAnalyzedStatus(@NonNull Long projectId);

  @Query("MATCH (p)-[:CONTAINS_COMMIT]->(c) WHERE ID(p) = {0} RETURN c ORDER BY c.timestamp DESC")
  @NonNull
  List<CommitEntity> findByProjectIdAndTimestampDesc(@NonNull Long projectId);

  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE ID(p) = {0} WITH c "
          + "OPTIONAL MATCH (c)-[r2:IS_CHILD_OF]-(c1) "
          + "OPTIONAL MATCH (c)<-[r:CHANGED_IN]-(f:FileEntity) RETURN DISTINCT c, c1, r, r2, f ORDER BY c.timestamp")
  @NonNull
  List<CommitEntity> findByProjectIdWithAllRelationshipsSortedByTimestampAsc(
      @NonNull Long projectId);

  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE ID(p) = {0} WITH c "
          + "OPTIONAL MATCH (c)<-[r:CHANGED_IN]-(f:FileEntity) RETURN DISTINCT c, r, f ORDER BY c.timestamp")
  @NonNull
  List<CommitEntity> findByProjectIdWithFileRelationshipsSortedByTimestampAsc(
      @NonNull Long projectId);

  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE ID(p) = {0} AND c.analyzed = FALSE WITH c "
          + "OPTIONAL MATCH (c)<-[r:CHANGED_IN]-(f:FileEntity) WHERE r.changeType <> \"DELETE\" AND any(x IN {1} WHERE f.path =~ x) "
          + "AND none(x IN {2} WHERE f.path =~ x) RETURN DISTINCT c, r, f ORDER BY c.timestamp")
  @NonNull
  List<CommitEntity> findByProjectIdNonAnalyzedWithFileRelationshipsSortedByTimestampAsc(
      @NonNull Long projectId, @NonNull List<String> includes, List<String> excludes);

  @Query("MATCH (p)-[:CONTAINS_COMMIT]->(c) WHERE ID(p) = {0} RETURN c")
  @NonNull
  List<CommitEntity> findByProjectId(@NonNull Long projectId);

  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->(c) WHERE ID(p) = {0} RETURN c ORDER BY c.timestamp DESC LIMIT 1")
  @NonNull
  CommitEntity findHeadCommit(@NonNull Long projectId);

  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE c.name = {0} AND ID(p) = {1} RETURN c")
  @NonNull
  Optional<CommitEntity> findByNameAndProjectId(@NonNull String commit, @NonNull Long projectId);

  @Query("MATCH (c:CommitEntity) WHERE ID(c) IN {0} SET c.analyzed = true")
  void setCommitsWithIDsAsAnalyzed(@NonNull long[] commitIds);

  @Query("MATCH (c) WHERE ID(c) IN {0} RETURN c")
  @NonNull
  List<CommitEntity> findAllById(@NonNull List<Long> commitIds);

  @Query("MATCH (p)-[:CONTAINS_COMMIT]->(c) WHERE c.name = {0} AND ID(p) = {1} RETURN c.timestamp")
  @NonNull
  Optional<Long> findTimeStampByNameAndProjectId(String commit, Long projectId);

  @Query(
      "UNWIND {0} as c "
          + "MATCH (c1) WHERE ID(c1) = c.id1 "
          + "MATCH (c2) WHERE ID(c2) = c.id2 "
          + "CREATE (c1)-[:IS_CHILD_OF]->(c2)")
  void createParentRelationships(List<HashMap<String, Object>> parentRels);

  @Query(
      "UNWIND {0} as x "
          + "MATCH (c) WHERE ID(c) = x.commitId "
          + "MATCH (f) WHERE ID(f) = x.fileId "
          + "CREATE (f)-[:CHANGED_IN {changeType: x.changeType, oldPath: x.oldPath}]->(c)")
  void createFileRelationships(List<HashMap<String, Object>> fileRels);
}
