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
   * Deletes a maximum of 10000 metrics in a project. This value can be adjusted as required by your
   * Neo4j installation in order to prevent out of memory errors.
   *
   * @param projectId The id of the project.
   * @return The number of deleted metrics, a maximum of 10000 at a time.
   */
  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->()<-[:VALID_FOR]-(mv) WHERE ID(p) = {0} "
          + "WITH mv LIMIT 10000 DETACH DELETE mv RETURN COUNT(mv)")
  long deleteProjectMetrics(long projectId);

  /**
   * Deletes a maximum of 10000 metrics in a project. This value can be adjusted as required by your
   * Neo4j installation in order to prevent out of memory errors.
   *
   * @param projectId The id of the project.
   * @return The number of deleted metrics, a maximum of 10000 at a time.
   */
  @Query(
      "MATCH (p)-[:CONTAINS*]->(f) WHERE ID(p) = {0} WITH f LIMIT 10000 DETACH DELETE f RETURN COUNT(f)")
  long deleteProjectFilesAndModules(long projectId);

  /**
   * Deletes all of the commits in a project.
   *
   * @param projectId The project id.
   */
  @Query("MATCH (p)-[:CONTAINS_COMMIT]->(c) WHERE ID(p) = {0} DETACH DELETE c")
  void deleteProjectCommits(long projectId);

  /**
   * Deletes all of the FilePatterns and AnalyzerConfigurationEntities in a project.
   *
   * @param projectId The project id.
   */
  @Query("MATCH (p)-[:HAS]->(a) WHERE ID(p) = {0} DETACH DELETE a")
  void deleteProjectConfiguration(long projectId);

  /**
   * Deletes all of the branches in a project. Must be called before the commits are deleted.
   *
   * @param projectId The project id.
   */
  @Query("MATCH (p)-[:HAS_BRANCH]->(b) WHERE ID(p) = {0} DETACH DELETE b")
  void deleteProjectBranches(long projectId);

  /** @return All projects that are not currently being deleted. */
  @Query("MATCH (p:ProjectEntity) WHERE p.isBeingDeleted = FALSE RETURN p ORDER BY p.name")
  @NonNull
  List<ProjectEntity> findAll();

  /**
   * @param name The name of the project.
   * @return The project with given name as long as it is not currently being deleted.
   */
  @Query("MATCH (p:ProjectEntity) WHERE p.name = {0} AND p.isBeingDeleted = FALSE RETURN p LIMIT 1")
  @NonNull
  Optional<ProjectEntity> findByName(@NonNull String name);

  /**
   * @param id The project id.
   * @return The project with the given id as long as it is not being deleted.
   */
  @Query("MATCH (p) WHERE ID(p) = {0} AND p.isBeingDeleted = FALSE RETURN p")
  @NonNull
  Optional<ProjectEntity> findById(long id);

  /**
   * @param id The project id.
   * @return The project with the given id with initialized [:CONTAINS] relationships for modules.
   *     <p>This query is possible without apoc and likely performs better, however the
   *     GraphEntityMapper complains about unsaturated relationships for some reason.
   */
  @Query(
      "MATCH (p) WHERE ID(p) = {0} AND p.isBeingDeleted = FALSE WITH p "
          + "CALL apoc.path.subgraphAll(p, {relationshipFilter:'CONTAINS>', labelFilter: '+ModuleEntity'}) "
          + "YIELD nodes, relationships RETURN p, nodes, relationships")
  @NonNull
  Optional<ProjectEntity> findByIdWithModules(long id);

  /**
   * @param id The project id.
   * @return True if the project is being processed, false otherwise.
   */
  @Query("MATCH (p) WHERE ID(p) = {0} RETURN p.isBeingProcessed")
  @NonNull
  Boolean isBeingProcessed(long id);

  /**
   * Sets the isBeingProcessed flag on a project.
   *
   * @param id The project id.
   * @param value The status.
   */
  @Query("MATCH (p) WHERE ID(p) = {0} SET p.isBeingProcessed = {1}")
  void setBeingProcessed(long id, @NonNull Boolean value);

  /**
   * @param id The project id.
   * @return True if a project with the given id exists, false otherwise.
   */
  @Query("MATCH (p:ProjectEntity) WHERE ID(p) = {0} RETURN COUNT(*) > 0")
  boolean existsById(long id);

  /**
   * @param name The name of the project.
   * @return True if a project with the given name exists and the project is not being deleted.
   */
  @Query(
      "MATCH (p:ProjectEntity) WHERE p.name = {0} AND p.isBeingDeleted = FALSE WITH p LIMIT 1 RETURN COUNT(*) > 0")
  boolean existsByName(@NonNull String name);

  /**
   * Sets the isBeingDeleted flag on a project.
   *
   * @param id The project id.
   * @param status The status.
   */
  @Query("MATCH (p) WHERE ID(p) = {0} SET p.isBeingDeleted = {1}")
  void setBeingDeleted(long id, @NonNull Boolean status);

  /**
   * Attaches existing file entities to a project. (Creates [:CONTAINS] relationships with every
   * file)
   *
   * @param projectId The project id.
   * @param fileIds A list of file ids.
   */
  @Query(
      "MATCH (p) WHERE ID(p) = {0} "
          + "MATCH (f) WHERE ID(f) IN {1} "
          + "CREATE (p)-[r:CONTAINS]->(f)")
  void attachFilesWithIds(long projectId, @NonNull List<Long> fileIds);

  /**
   * Attaches existing commit entities to a project. (Creates [:CONTAINS_COMMIT] relationships with
   * every commit)
   *
   * @param projectId The project id.
   * @param commitIds A list of file ids.
   */
  @Query(
      "MATCH (p) WHERE ID(p) = {0} "
          + "MATCH (c) WHERE ID(c) IN {1} "
          + "CREATE (p)-[r:CONTAINS_COMMIT]->(c)")
  void attachCommitsWithIds(long projectId, @NonNull List<Long> commitIds);

  /** @param projectId */
  @Query("MATCH (p)<-[r:WORKS_ON]-() WHERE ID(p) = {0} DELETE r")
  void deleteContributorRelationships(long projectId);

  /**
   * @param userId The user id.
   * @return All the project a user is assigned to.
   */
  @Query("MATCH (u)-[:ASSIGNED_TO]->(p) WHERE ID(u) = {0} RETURN p")
  List<ProjectEntity> findProjectsByUsedId(long userId);

  /**
   * @param teamId The team id.
   * @return All the project a team is assigned to.
   */
  @Query("MATCH (t)-[:ASSIGNED_TO]->(p) WHERE ID(t) = {0} RETURN p")
  List<ProjectEntity> listProjectByTeamId(long teamId);
}
