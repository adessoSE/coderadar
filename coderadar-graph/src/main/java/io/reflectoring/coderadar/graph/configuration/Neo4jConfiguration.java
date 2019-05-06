package io.reflectoring.coderadar.graph.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@Configuration
@EnableNeo4jRepositories(basePackages = "io.reflectoring.coderadar.graph")
@EntityScan(basePackages = "io.reflectoring.coderadar.graph")
public class Neo4jConfiguration {
}
