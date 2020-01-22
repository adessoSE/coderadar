package io.reflectoring.coderadar.graph.projectadministration.module.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends Neo4jRepository<ModuleEntity, Long> {
  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS*1..]->(m:ModuleEntity) WHERE ID(p) = {0} RETURN m ORDER BY m.path DESC")
  @NonNull
  List<ModuleEntity> findModulesInProjectSortedDesc(@NonNull Long projectId);

  @Query("MATCH (p:ProjectEntity)-[:CONTAINS*1..]->(m:ModuleEntity) WHERE ID(p) = {0} RETURN m")
  @NonNull
  List<ModuleEntity> findModulesInProject(@NonNull Long projectId);

  @Query(
      "CREATE (m:ModuleEntity { path: {1} }) WITH m "
          + "MATCH (p:ProjectEntity)-[r1:CONTAINS]->(f:FileEntity) WHERE ID(p) = {0} AND f.path STARTS WITH {1} WITH DISTINCT m, p, f, r1 "
          + "CREATE (m)-[r2:CONTAINS]->(f) WITH DISTINCT m,f,p,r1 "
          + "DELETE r1 WITH m, p LIMIT 1 "
          + "CREATE (p)-[r3:CONTAINS]->(m) RETURN m")
  @NonNull
  ModuleEntity createModuleInProject(@NonNull Long projectId, @NonNull String modulePath);

  @Query(
      "CREATE (m:ModuleEntity { path: {1} }) WITH m "
          + "MATCH (m2:ModuleEntity)-[r1:CONTAINS]->(f:FileEntity) WHERE ID(m2) = {0} AND f.path STARTS WITH {1} WITH DISTINCT m, m2, f, r1 "
          + "CREATE (m)-[r2:CONTAINS]->(f) WITH DISTINCT m,f,m2,r1 "
          + "DELETE r1 WITH m, m2 LIMIT 1 "
          + "CREATE (m2)-[r3:CONTAINS]->(m) RETURN m")
  @NonNull
  ModuleEntity createModuleInModule(@NonNull Long moduleId, @NonNull String modulePath);

  @Query(
      "MATCH (e)-[:CONTAINS]->(f:FileEntity) WHERE ID(e) = {1} AND f.path STARTS WITH {0} RETURN COUNT(f) > 0 ")
  @NonNull
  Boolean fileInPathExists(@NonNull String path, @NonNull Long projectOrModuleId);

  @Query(
      "MATCH (p:ProjectEntity)-[r:CONTAINS]->(m:ModuleEntity) WHERE ID(p) = {0} AND ID(m) = {1} DELETE r")
  void detachModuleFromProject(@NonNull Long projectId, @NonNull Long moduleId);

  @Query(
      "MATCH (m1:ModuleEntity)-[r:CONTAINS]->(m2:ModuleEntity) WHERE ID(m1) = {0} AND ID(m2) = {1} DELETE r")
  void detachModuleFromModule(@NonNull Long moduleId1, @NonNull Long moduleId2);
}
