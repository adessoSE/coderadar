package io.reflectoring.coderadar.graph.projectadministration.module.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends Neo4jRepository<ModuleEntity, Long> {

  /**
   * @param projectId The project id.
   * @return All of the modules in a project sorted by the path in descending order.
   */
  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS*1..]->(m:ModuleEntity) WHERE ID(p) = {0} RETURN m ORDER BY m.path DESC")
  @NonNull
  List<ModuleEntity> findModulesInProjectSortedDesc(@NonNull Long projectId);

  /**
   * @param projectId The project id.
   * @return All of the modules in a project.
   */
  @Query("MATCH (p:ProjectEntity)-[:CONTAINS*1..]->(m:ModuleEntity) WHERE ID(p) = {0} RETURN m")
  @NonNull
  List<ModuleEntity> findModulesInProject(@NonNull Long projectId);

  /**
   * Detaches all files that fall inside the modulePath from the project, creates a new ModuleEntity
   * and attaches said files to it.
   *
   * @param projectId The project id.
   * @param modulePath The path of the new module.
   * @return The id of the newly created module.
   */
  @Query(
      "CREATE (m:ModuleEntity { path: {1} }) WITH m "
          + "MATCH (p)-[r1:CONTAINS]->(f:FileEntity) WHERE ID(p) = {0} AND f.path STARTS WITH {1} WITH DISTINCT m, p, f, r1 "
          + "CREATE (m)-[r2:CONTAINS]->(f) WITH DISTINCT m,f,p,r1 "
          + "DELETE r1 WITH m, p LIMIT 1 "
          + "CREATE (p)-[r3:CONTAINS]->(m) RETURN m")
  @NonNull
  ModuleEntity createModuleInProject(@NonNull Long projectId, @NonNull String modulePath);

  /**
   * Detaches all files that fall inside the modulePath from the existing parent module, creates a
   * new ModuleEntity and attaches said files to it.
   *
   * @param moduleId The parent module id.
   * @param modulePath The path of the new module.
   * @return The id of the newly created module.
   */
  @Query(
      "CREATE (m:ModuleEntity { path: {1} }) WITH m "
          + "MATCH (m2)-[r1:CONTAINS]->(f:FileEntity) WHERE ID(m2) = {0} AND f.path STARTS WITH {1} WITH DISTINCT m, m2, f, r1 "
          + "CREATE (m)-[r2:CONTAINS]->(f) WITH DISTINCT m,f,m2,r1 "
          + "DELETE r1 WITH m, m2 LIMIT 1 "
          + "CREATE (m2)-[r3:CONTAINS]->(m) RETURN m")
  @NonNull
  ModuleEntity createModuleInModule(@NonNull Long moduleId, @NonNull String modulePath);

  /**
   * @param path The path to search in.
   * @param projectOrModuleId The id of the project or a module.
   * @return True if any file falls under the given path, false otherwise.
   */
  @Query(
      "MATCH (p)-[:CONTAINS]->(f:FileEntity) WHERE ID(p) = {1} AND f.path STARTS WITH {0} RETURN COUNT(f) > 0 ")
  @NonNull
  Boolean fileInPathExists(@NonNull String path, @NonNull Long projectOrModuleId);

  /**
   * Detaches a module from a project while keeping all of it's files still attached to it.
   *
   * @param projectId The project id.
   * @param moduleId The id of the module to detach.
   */
  @Query("MATCH (p)-[r]->(m) WHERE ID(p) = {0} AND ID(m) = {1} DELETE r")
  void detachModuleFromProject(@NonNull Long projectId, @NonNull Long moduleId);

  /**
   * Detaches a module from it's parent module while keeping all of it's files still attached to the
   * child.
   *
   * @param parentId The id of the parent module.
   * @param childId The id of the module to detach.
   */
  @Query("MATCH (m1)-[r]->(m2) WHERE ID(m1) = {0} AND ID(m2) = {1} DELETE r")
  void detachModuleFromModule(@NonNull Long parentId, @NonNull Long childId);
}
