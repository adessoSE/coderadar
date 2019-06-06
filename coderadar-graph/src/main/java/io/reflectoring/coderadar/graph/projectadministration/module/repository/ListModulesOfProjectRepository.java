package io.reflectoring.coderadar.graph.projectadministration.module.repository;

import io.reflectoring.coderadar.projectadministration.domain.Module;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListModulesOfProjectRepository extends Neo4jRepository<Module, Long> {

  @Query("MATCH (p:Project)-[:HAS]->(m:Module) WHERE ID(p) = {0} RETURN m")
  List<Module> findByProjectId(Long projectId);
}
