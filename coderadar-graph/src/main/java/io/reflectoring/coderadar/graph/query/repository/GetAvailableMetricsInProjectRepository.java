package io.reflectoring.coderadar.graph.query.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GetAvailableMetricsInProjectRepository extends Neo4jRepository {
  // TODO: Add query to find a available metrics in spezific project.
  List<String> findMetricsInProject(long projectId);
}
