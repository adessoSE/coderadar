package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.analyzer.domain.FindingEntity;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FindingRepository extends Neo4jRepository<FindingEntity, Long> {
  @Query(
      "MATCH (p:ProjectEntity)-->(f:FileEntity)-->(m:MetricValueEntity)-->(fi:FindingEntity) "
          + "WHERE ID(p) = {0} RETURN fi")
  List<FindingEntity> findByProjectId(Long projectId);
}
