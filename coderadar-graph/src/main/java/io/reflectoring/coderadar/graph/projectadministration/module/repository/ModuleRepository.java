package io.reflectoring.coderadar.graph.projectadministration.module.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends Neo4jRepository<ModuleEntity, Long> {
  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS*1..]->(m:ModuleEntity) WHERE ID(p) = {0} RETURN m ORDER BY m.path DESC")
  List<ModuleEntity> findModulesInProjectSortedDesc(Long projectId);

  @Query("MATCH (p:ProjectEntity)-[:CONTAINS*1..]->(m:ModuleEntity) WHERE ID(p) = {0} RETURN m")
  List<ModuleEntity> findModulesInProject(Long projectId);

  @Query(
      "CREATE (m:ModuleEntity { path: {1} }) WITH m "
          + "MATCH (p:ProjectEntity)-[r1:CONTAINS]->(f:FileEntity) WHERE ID(p) = {0} AND f.path STARTS WITH {1} WITH DISTINCT m, p, f, r1 "
          + "CREATE (m)-[r2:CONTAINS]->(f) WITH DISTINCT m,f,p,r1 "
          + "DELETE r1 WITH m, p LIMIT 1 "
          + "CREATE (p)-[r3:CONTAINS]->(m) RETURN m")
  ModuleEntity createModuleInProject(Long projectId, String modulePath);

  @Query(
      "CREATE (m:ModuleEntity { path: {1} }) WITH m "
          + "MATCH (m2:ModuleEntity)-[r1:CONTAINS]->(f:FileEntity) WHERE ID(m2) = {0} AND f.path STARTS WITH {1} WITH DISTINCT m, m2, f, r1 "
          + "CREATE (m)-[r2:CONTAINS]->(f) WITH DISTINCT m,f,m2,r1 "
          + "DELETE r1 WITH m, m2 LIMIT 1 "
          + "CREATE (m2)-[r3:CONTAINS]->(m) RETURN m")
  ModuleEntity createModuleInModule(Long moduleId, String modulePath);

  @Query(
      "MATCH (p:ProjectEntity)-[r:CONTAINS]->(m:ModuleEntity) WHERE ID(p) = {projectId} AND ID(m) = {moduleId} DELETE r")
  void detachModuleFromProject(
      @Param("projectId") Long projectId, @Param("moduleId") Long moduleId);

  @Query(
      "MATCH (m1:ModuleEntity)-[r:CONTAINS]->(m2:ModuleEntity) WHERE ID(m1) = {moduleId1} AND ID(m2) = {moduleId2} DELETE r")
  void detachModuleFromModule(
      @Param("moduleId1") Long moduleId1, @Param("moduleId2") Long moduleId2);
}
