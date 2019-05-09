package io.reflectoring.coderadar.graph.projectadministration.module.repository;

import io.reflectoring.coderadar.core.projectadministration.domain.Module;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListModulesOfProjectRepository extends Neo4jRepository<Module, Long> {
  List<Module> findByProject_Id(Long projectId);
}
