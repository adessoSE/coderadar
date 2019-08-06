package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.graph.analyzer.domain.FileEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface FileRepository extends Neo4jRepository<FileEntity, Long> {

    @Query("MATCH (p:ProjectEntity)-->(f:FileEntity) WHERE ID(p) = {1} AND f.path = {0} RETURN f")
    FileEntity findByPathInProject(String path, Long projectId);
}
