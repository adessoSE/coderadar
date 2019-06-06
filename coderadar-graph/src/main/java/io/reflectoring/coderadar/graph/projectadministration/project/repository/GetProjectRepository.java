package io.reflectoring.coderadar.graph.projectadministration.project.repository;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import java.util.Optional;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GetProjectRepository extends Neo4jRepository<Project, Long> {
  Optional<Project> findByName(String name);
}
