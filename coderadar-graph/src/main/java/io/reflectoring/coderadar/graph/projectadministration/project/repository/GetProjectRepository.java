package io.reflectoring.coderadar.graph.projectadministration.project.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GetProjectRepository extends Neo4jRepository<ProjectEntity, Long> {
  Optional<ProjectEntity> findByName(String name);

  List<ProjectEntity> findAllByName(String name);
}
