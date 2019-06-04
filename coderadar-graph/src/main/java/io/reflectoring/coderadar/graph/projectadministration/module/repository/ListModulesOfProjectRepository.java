package io.reflectoring.coderadar.graph.projectadministration.module.repository;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ListModulesOfProjectRepository extends Neo4jRepository<Module, Long> {

  // @Query("MATCH (m:Module) WHERE (m.project.id = {projectId}) RETURN m")
  default List<Module> findByProjectId(@Param("projectId") Long projectId) {
    List<Module> result = new ArrayList<>();
    Iterable<Module> modules = findAll();
    for (Module module : modules) {
      if (module.getProject().getId().equals(projectId)) {
        result.add(module);
      }
    }
    return result;
  }
}
