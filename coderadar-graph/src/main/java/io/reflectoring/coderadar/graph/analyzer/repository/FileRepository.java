package io.reflectoring.coderadar.graph.analyzer.repository;

import io.reflectoring.coderadar.analyzer.domain.File;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface FileRepository extends Neo4jRepository<File, Long> {}
