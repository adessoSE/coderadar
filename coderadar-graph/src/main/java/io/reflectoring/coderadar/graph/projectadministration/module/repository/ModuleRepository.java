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
      "MATCH (p)-[:CONTAINS*1..]->(m:ModuleEntity) USING SCAN m:ModuleEntity WHERE ID(p) = {0} RETURN m ORDER BY m.path DESC")
  @NonNull
  List<ModuleEntity> findModulesInProjectSortedDesc(long projectId);

  /**
   * Detaches all files that fall inside the modulePath from the project/module, creates a new
   * ModuleEntity and attaches said files to it.
   *
   * @param projectOrModuleId The project or module id.
   * @param modulePath The path of the new module.
   * @return The id of the newly created module.
   */
  @Query(
      "MATCH (p) WHERE ID(p) = {0} WITH p "
          + "CREATE (m:ModuleEntity { path: {1}} )<-[:CONTAINS]-(p) WITH m, p LIMIT 1 "
          + "MATCH (p)-[r1:CONTAINS]->(f:FileEntity) WHERE f.path STARTS WITH {1} WITH DISTINCT m, f, r1 "
          + "CREATE (m)-[r2:CONTAINS]->(f) WITH DISTINCT m,f,r1 "
          + "DELETE r1 WITH m "
          + "RETURN m")
  @NonNull
  ModuleEntity createModule(long projectOrModuleId, @NonNull String modulePath);

  /**
   * Detaches a module from a project while keeping all of it's files still attached to it.
   *
   * @param projectId The project id.
   * @param moduleId The id of the module to detach.
   */
  @Query("MATCH (p)-[r]->(m) WHERE ID(p) = {0} AND ID(m) = {1} DELETE r")
  void detachModuleFromProject(long projectId, long moduleId);

  /**
   * Detaches a module from it's parent module while keeping all of it's files still attached to the
   * child.
   *
   * @param parentId The id of the parent module.
   * @param childId The id of the module to detach.
   */
  @Query("MATCH (m1)-[r]->(m2) WHERE ID(m1) = {0} AND ID(m2) = {1} DELETE r")
  void detachModuleFromModule(long parentId, long childId);
}
