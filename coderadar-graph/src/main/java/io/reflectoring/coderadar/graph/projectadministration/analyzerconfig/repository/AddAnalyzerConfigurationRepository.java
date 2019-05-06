package io.reflectoring.coderadar.graph.projectadministration.analyzerconfig.repository;

import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddAnalyzerConfigurationRepository
    extends Neo4jRepository<AnalyzerConfiguration, Long> {}
