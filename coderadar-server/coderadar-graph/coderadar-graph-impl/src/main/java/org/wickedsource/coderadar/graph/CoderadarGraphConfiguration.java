package org.wickedsource.coderadar.graph;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableNeo4jRepositories("org.wickedsource.coderadar.graph.domain")
@EnableTransactionManagement
public class CoderadarGraphConfiguration {}
