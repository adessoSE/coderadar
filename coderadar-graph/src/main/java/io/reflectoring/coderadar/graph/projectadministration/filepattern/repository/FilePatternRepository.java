package io.reflectoring.coderadar.graph.projectadministration.filepattern.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.FilePatternEntity;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface FilePatternRepository extends Neo4jRepository<FilePatternEntity, Long> {

  /**
   * @param projectId The project id.
   * @return All file patterns in a project.
   */
  @Query("MATCH (p)-[:HAS]->(f:FilePatternEntity) WHERE ID(p) = $0 RETURN f")
  @NonNull
  List<FilePatternEntity> findByProjectId(long projectId);

  @Query("MATCH (f:FilePatternEntity) WHERE ID(f) = $0 RETURN COUNT(f) > 0")
  boolean existsById(long id);
}
