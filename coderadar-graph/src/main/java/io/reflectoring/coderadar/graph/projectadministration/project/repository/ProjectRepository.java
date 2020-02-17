package io.reflectoring.coderadar.graph.projectadministration.project.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends Neo4jRepository<ProjectEntity, Long> {

  /**
   * Deletes a maximum of 10000 findings in a project. This value can be adjusted as required by
   * your Neo4j installation in order to prevent out of memory errors.
   *
   * @param projectId The id of the project.
   * @return The number of deleted findings, a maximum of 10000 at a time.
   */
  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->()<-[:VALID_FOR]-()-[:LOCATED_IN]->(fi) "
          + "WHERE ID(p) = {0} WITH fi LIMIT 10000 DETACH DELETE fi RETURN COUNT(fi)")
  long deleteProjectFindings(@NonNull Long projectId);

  /**
   * Deletes a maximum of 10000 metrics in a project. This value can be adjusted as required by your
   * Neo4j installation in order to prevent out of memory errors.
   *
   * @param projectId The id of the project.
   * @return The number of deleted metrics, a maximum of 10000 at a time.
   */
  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->()<-[:VALID_FOR]-(mv) WHERE ID(p) = {0} "
          + "WITH mv LIMIT 10000 DETACH DELETE mv RETURN COUNT(mv)")
  long deleteProjectMetrics(@NonNull Long projectId);

  /**
   * Deletes a maximum of 10000 metrics in a project. This value can be adjusted as required by your
   * Neo4j installation in order to prevent out of memory errors.
   *
   * @param projectId The id of the project.
   * @return The number of deleted metrics, a maximum of 10000 at a time.
   */
  @Query(
      "MATCH (p)-[:CONTAINS*]->(f) WHERE ID(p) = {0} WITH f LIMIT 10000 DETACH DELETE f RETURN COUNT(f)")
  long deleteProjectFilesAndModules(@NonNull Long projectId);

  @Query("MATCH (p)-[:CONTAINS_COMMIT]->(c) WHERE ID(p) = {0} DETACH DELETE c")
  void deleteProjectCommits(@NonNull Long projectId);

  @Query("MATCH (p)-[:HAS]->(a) WHERE ID(p) = {0} DETACH DELETE a")
  void deleteProjectConfiguration(@NonNull Long projectId);

  @Query("MATCH (p)-[:CONTAINS_COMMIT]->()<-[:POINTS_TO]-(b) WHERE ID(p) = {0} DETACH DELETE b")
  void deleteProjectBranches(@NonNull Long projectId);

  @Query("MATCH (p:ProjectEntity) WHERE p.isBeingDeleted = FALSE RETURN p")
  @NonNull
  List<ProjectEntity> findAll();

  @Query("MATCH (p:ProjectEntity) WHERE p.name = {0} AND p.isBeingDeleted = FALSE RETURN p LIMIT 1")
  @NonNull
  Optional<ProjectEntity> findByName(@NonNull String name);

  @Query("MATCH (p) WHERE ID(p) = {0} AND p.isBeingDeleted = FALSE RETURN p")
  @NonNull
  Optional<ProjectEntity> findById(@NonNull Long id);

  @Query(
      "MATCH (p) WHERE ID(p) = {0} AND p.isBeingDeleted = FALSE WITH p "
          + "OPTIONAL MATCH (p)-[r:CONTAINS]->(m:ModuleEntity) "
          + "RETURN p, r, m")
  @NonNull
  Optional<ProjectEntity> findByIdWithModules(@NonNull Long id);

  @Query("MATCH (p) WHERE ID(p) = {0} RETURN p.isBeingProcessed")
  @NonNull
  Boolean isBeingProcessed(@NonNull Long id);

  @Query("MATCH (p) WHERE ID(p) = {0} SET p.isBeingProcessed = {1}")
  void setBeingProcessed(@NonNull Long id, @NonNull Boolean value);

  @Query("MATCH (p) WHERE ID(p) = {0} RETURN COUNT(*) > 0")
  boolean existsById(@NonNull Long id);

  @Query(
      "MATCH (p:ProjectEntity) WHERE p.name = {0} AND p.isBeingDeleted = FALSE RETURN COUNT(*) > 0")
  boolean existsByName(@NonNull String name);

  @Query("MATCH (p) WHERE ID(p) = {0} RETURN p.analyzingStatus")
  @NonNull
  Boolean getProjectAnalyzingStatus(@NonNull Long projectId);

  @Query("MATCH (p) WHERE ID(p) = {0} SET p.analyzingStatus = {1}")
  void setAnalyzingStatus(@NonNull Long projectId, @NonNull Boolean b);

  @Query("MATCH (p) WHERE ID(p) = {0} SET p.isBeingDeleted = {1}")
  void setBeingDeleted(@NonNull Long id, @NonNull Boolean value);

  @Query(
      "MATCH (p) WHERE ID(p) = {0} "
          + "MATCH (f) WHERE ID(f) IN {1} "
          + "CREATE (p)-[r:CONTAINS]->(f)")
  void attachFilesWithIds(Long projectId, List<Long> fileIds);

  @Query(
      "MATCH (p) WHERE ID(p) = {0} "
          + "MATCH (c) WHERE ID(c) IN {1} "
          + "CREATE (p)-[r:CONTAINS_COMMIT]->(c)")
  void attachCommitsWithIds(Long projectId, List<Long> commitIds);
}
