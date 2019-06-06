package io.reflectoring.coderadar.graph.projectadministration.filepattern.repository;

import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListFilePatternsOfProjectRepository extends Neo4jRepository<FilePattern, Long> {

  @Query("MATCH (p:Project)-[:HAS]->(f:FilePattern) WHERE ID(p) = {0} RETURN f")
  List<FilePattern> findByProjectId(Long projectId);
}
