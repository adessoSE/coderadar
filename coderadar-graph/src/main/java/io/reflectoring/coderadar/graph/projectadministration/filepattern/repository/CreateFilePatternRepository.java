package io.reflectoring.coderadar.graph.projectadministration.filepattern.repository;

import io.reflectoring.coderadar.core.projectadministration.domain.FilePattern;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreateFilePatternRepository extends Neo4jRepository<FilePattern, Long> {
}
