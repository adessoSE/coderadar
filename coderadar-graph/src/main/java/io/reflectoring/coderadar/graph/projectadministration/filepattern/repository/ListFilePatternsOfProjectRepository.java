package io.reflectoring.coderadar.graph.projectadministration.filepattern.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.FilePatternEntity;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListFilePatternsOfProjectRepository
    extends Neo4jRepository<FilePatternEntity, Long> {

  @Query("MATCH (p:ProjectEntity)-[:HAS]->(f:FilePatternEntity) WHERE ID(p) = {0} RETURN f")
  List<FilePatternEntity> findByProjectId(Long projectId);
}
