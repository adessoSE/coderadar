package org.wickedsource.coderadar.graph;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableNeo4jRepositories
@EnableTransactionManagement
public class CoderadarGraphConfiguration {

  @Bean
  public SessionFactory sessionFactory() {
    // with domain entity base package(s)
    return new SessionFactory("org.wickedsource.coderadar.graph");
  }

  @Bean
  public Neo4jTransactionManager transactionManager() {
    return new Neo4jTransactionManager(sessionFactory());
  }
}
