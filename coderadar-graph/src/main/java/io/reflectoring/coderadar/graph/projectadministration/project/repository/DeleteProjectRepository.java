package io.reflectoring.coderadar.graph.projectadministration.project.repository;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeleteProjectRepository extends Neo4jRepository<Project, Long> {
  @Query("MATCH (p:Project), (p)-[*0..]->(c) WHERE ID(p) = {projectId} DETACH DELETE p, c")
  void deleteProjectCascade(@Param("projectId") Long projectId);
}
