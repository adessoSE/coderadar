package org.wickedsource.coderadar.graph;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableNeo4jRepositories(basePackages = "org.wickedsource.coderadar.graph.domain")
@EnableTransactionManagement
public class CoderadarGraphConfiguration {

  @Bean
  public Neo4jTransactionManager transactionManager() throws Exception {
    return new Neo4jTransactionManager(sessionFactory());
  }

  @Bean
  public SessionFactory sessionFactory() {
    return new SessionFactory("org.wickedsource.coderadar.graph.domain");
  }
}
