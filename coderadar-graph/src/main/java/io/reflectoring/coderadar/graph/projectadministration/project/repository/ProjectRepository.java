package io.reflectoring.coderadar.graph.projectadministration.project.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends Neo4jRepository<ProjectEntity, Long> {

  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS_COMMIT]->()<-[:VALID_FOR]-()-[:LOCATED_IN]->(fi:FindingEntity) "
          + "WHERE ID(p) = {projectId} "
          + "DETACH DELETE fi ")
  void deleteProjectFindings(@Param("projectId") Long projectId);

  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS_COMMIT]->()<-[:VALID_FOR]-(mv:MetricValueEntity) WHERE ID(p) = {projectId} "
          + "DETACH DELETE mv ")
  void deleteProjectMetrics(@Param("projectId") Long projectId);

  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS*]->(f:FileEntity) WHERE ID(p) = {projectId} "
          + "OPTIONAL MATCH (f)<-[:CONTAINS]-(m:ModuleEntity) "
          + "DETACH DELETE m, f")
  void deleteProjectFilesAndModules(@Param("projectId") Long projectId);

  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS_COMMIT]->(c:CommitEntity) WHERE ID(p) = {projectId} DETACH DELETE c")
  void deleteProjectCommits(@Param("projectId") Long projectId);

  @Query("MATCH (p:ProjectEntity)-[:HAS]->(a) WHERE ID(p) = {projectId} DETACH DELETE a")
  void deleteProjectConfiguration(@Param("projectId") Long projectId);

  /** For some reason this is much faster than the default findAll() */
  @Query("MATCH (p:ProjectEntity) RETURN p")
  List<ProjectEntity> findAllProjects();

  @Query("MATCH (p:ProjectEntity) WHERE p.name = {0} RETURN p LIMIT 1")
  Optional<ProjectEntity> findByName(String name);

  @Query("MATCH (p:ProjectEntity) WHERE ID(p) = {0} RETURN p")
  Optional<ProjectEntity> findProjectById(Long id);

  @Query("MATCH (p:ProjectEntity) WHERE p.name = {0} RETURN p")
  List<ProjectEntity> findAllByName(String name);

  @Query("MATCH (p:ProjectEntity) WHERE ID(p) = {0} RETURN p.isBeingProcessed")
  Boolean isBeingProcessed(Long id);

  @Query("MATCH (p:ProjectEntity) WHERE ID(p) = {0} SET p.isBeingProcessed = {1}")
  void setBeingProcessed(Long id, Boolean value);
}
