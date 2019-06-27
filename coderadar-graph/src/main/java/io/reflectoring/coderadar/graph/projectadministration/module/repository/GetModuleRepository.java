package io.reflectoring.coderadar.graph.projectadministration.module.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.ModuleEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GetModuleRepository extends Neo4jRepository<ModuleEntity, Long> {}
