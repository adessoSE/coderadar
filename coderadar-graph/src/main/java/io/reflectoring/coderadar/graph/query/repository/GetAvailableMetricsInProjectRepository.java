package io.reflectoring.coderadar.graph.query.repository;

import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface GetAvailableMetricsInProjectRepository extends Neo4jRepository {

  @Query(
      "MATCH (p)-[:CONTAINS_COMMIT]->()<-[:VALID_FOR]-(mv) WHERE ID(p) = {0} RETURN DISTINCT mv.name")
  @NonNull
  List<String> getAvailableMetricsInProject(@NonNull Long projectId);
}
