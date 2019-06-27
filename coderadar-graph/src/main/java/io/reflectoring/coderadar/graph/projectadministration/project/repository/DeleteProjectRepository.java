package io.reflectoring.coderadar.graph.projectadministration.project.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeleteProjectRepository extends Neo4jRepository<ProjectEntity, Long> {
  @Query("MATCH (p:ProjectEntity), (p)-[*0..]->(c) WHERE ID(p) = {projectId} DETACH DELETE p, c")
  void deleteProjectCascade(@Param("projectId") Long projectId);
}
