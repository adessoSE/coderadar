package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.analyzer.domain.CommitEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CommitRepository extends Neo4jRepository<CommitEntity, Long> {
    @Query("MATCH (p1:ProjectEntity)-[:CONTAINS*]-(f1:FileEntity)-[:CHANGED_IN]->(c1:CommitEntity)-[:IS_CHILD_OF*0..2]-(c2:CommitEntity) " +
            "WHERE ID(p1) = {0} UNWIND [c1, c2] AS c WITH DISTINCT c SET c.analyzed = false")
    void resetAnalyzedStatus(Long projectId);
}
