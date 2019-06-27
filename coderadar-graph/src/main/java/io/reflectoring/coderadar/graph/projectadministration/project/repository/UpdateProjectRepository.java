package io.reflectoring.coderadar.graph.projectadministration.project.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateProjectRepository extends Neo4jRepository<ProjectEntity, Long> {}
