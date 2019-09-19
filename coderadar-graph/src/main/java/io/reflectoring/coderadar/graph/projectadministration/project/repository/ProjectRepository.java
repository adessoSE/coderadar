package io.reflectoring.coderadar.graph.projectadministration.project.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends Neo4jRepository<ProjectEntity, Long> {
  Optional<ProjectEntity> findByName(String name);

  List<ProjectEntity> findAllByName(String name);

  @Query(
      "MATCH (p:ProjectEntity)-[r*]-(c) WHERE ID(p) = {projectId} FOREACH(re IN r | DETACH DELETE p, re, c)")
  void deleteProjectCascade(@Param("projectId") Long projectId);
}
