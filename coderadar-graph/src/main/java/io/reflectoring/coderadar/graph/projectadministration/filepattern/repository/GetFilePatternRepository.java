package io.reflectoring.coderadar.graph.projectadministration.filepattern.repository;

import io.reflectoring.coderadar.graph.projectadministration.domain.FilePatternEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GetFilePatternRepository extends Neo4jRepository<FilePatternEntity, Long> {}
