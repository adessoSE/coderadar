package io.reflectoring.coderadar.graph.projectadministration.module.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends Neo4jRepository<ModuleEntity, Long> {
  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS*1..]->(m:ModuleEntity) WHERE ID(p) = {0} RETURN m ORDER BY m.path")
  List<ModuleEntity> findModulesInProjectSortedDesc(Long projectId);

  @Query(
      "MATCH (p:ProjectEntity)-[:CONTAINS*1..]->(m:ModuleEntity) WHERE ID(p) = {0} RETURN m ORDER BY m.path")
  List<ModuleEntity> findModulesInProject(Long projectId);

  @Query(
      "MATCH (p:ProjectEntity)-[r:CONTAINS]->(f:FileEntity) WHERE ID(p) = {projectId} AND ID(f) IN {fileIds} DELETE r")
  void detachFilesFromProject(
      @Param("projectId") Long projectId, @Param("fileIds") List<Long> fileIds);

  @Query(
      "MATCH (p:ModuleEntity)-[r:CONTAINS]->(f:FileEntity) WHERE ID(p) = {moduleId} AND ID(f) IN {fileIds} DELETE r")
  void detachFilesFromModule(
      @Param("moduleId") Long moduleId, @Param("fileIds") List<Long> fileIds);

  @Query(
      "MATCH (p:ProjectEntity)-[r:CONTAINS]->(m:ModuleEntity) WHERE ID(p) = {projectId} AND ID(m) = {moduleId} DELETE r")
  void detachModuleFromProject(
      @Param("projectId") Long projectId, @Param("moduleId") Long moduleId);

  @Query(
      "MATCH (m1:ModuleEntity)-[r:CONTAINS]->(m2:ModuleEntity) WHERE ID(m1) = {moduleId1} AND ID(m2) = {moduleId2} DELETE r")
  void detachModuleFromModule(
      @Param("moduleId1") Long moduleId1, @Param("moduleId2") Long moduleId2);

  @Query("MATCH (m:ModuleEntity) WHERE ID(m) = {0} RETURN m")
  Optional<ModuleEntity> findModuleById(Long id);
}
