package io.reflectoring.coderadar.graph.projectadministration.module.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListModulesOfProjectRepository extends Neo4jRepository<ModuleEntity, Long> {

  @Query("MATCH (p:ProjectEntity)-[:CONTAINS*1..]->(m:ModuleEntity) WHERE ID(p) = {0} RETURN m")
  List<ModuleEntity> findModulesInProject(Long projectId);
}
