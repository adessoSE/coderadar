package io.reflectoring.coderadar.graph.projectadministration.filepattern.repository;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ListFilePatternsOfProjectRepository extends Neo4jRepository<FilePattern, Long> {

  // @Query(value = "MATCH (p:Project) WHERE ID(p) = {projectId} RETURN p.filePatterns")
  default List<FilePattern> findByProjectId(@Param("projectId") Long projectId) {
    List<FilePattern> result = new ArrayList<>();
    Iterable<FilePattern> filePatterns = findAll();
    for (FilePattern filePattern : filePatterns) {
      if (filePattern.getProject().getId().equals(projectId)) {
        result.add(filePattern);
      }
    }
    return result;
  }
}
