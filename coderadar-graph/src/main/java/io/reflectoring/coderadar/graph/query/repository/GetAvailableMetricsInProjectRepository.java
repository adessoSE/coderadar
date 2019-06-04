package io.reflectoring.coderadar.graph.query.repository;

import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GetAvailableMetricsInProjectRepository extends Neo4jRepository {
  // TODO: Add query to find a available metrics in specific project.
  @Query(value = "MATCH (p:Project) WHERE ID(p) = {projectId}")
  List<String> getAvailableMetricsInProject(@Param("projectId") long projectId);
}
