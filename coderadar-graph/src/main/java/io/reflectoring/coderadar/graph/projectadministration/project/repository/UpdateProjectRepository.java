package io.reflectoring.coderadar.graph.projectadministration.project.repository;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateProjectRepository extends Neo4jRepository<Project, Long> {}
