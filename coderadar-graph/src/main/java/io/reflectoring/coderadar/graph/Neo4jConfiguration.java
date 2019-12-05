package io.reflectoring.coderadar.graph;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableNeo4jRepositories(basePackages = "io.reflectoring.coderadar.graph")
@EnableConfigurationProperties(value = Neo4jProperties.class)
public class Neo4jConfiguration {
  private final Neo4jProperties properties;

  public Neo4jConfiguration(Neo4jProperties properties) {
    this.properties = properties;
  }

  @Bean
  @ConditionalOnMissingBean
  public org.neo4j.ogm.config.Configuration configuration() {
    return this.properties.createConfiguration();
  }

  @Bean(name = "transactionManager")
  public PlatformTransactionManager transactionManager() {
    Neo4jTransactionManager txManager = new Neo4jTransactionManager();
    txManager.setSessionFactory(sessionFactory(configuration()));
    return txManager;
  }

  @Bean
  public SessionFactory sessionFactory(org.neo4j.ogm.config.Configuration configuration) {
    return new SessionFactory(
        configuration,
        "io.reflectoring.coderadar.graph.projectadministration.domain",
        "io.reflectoring.coderadar.graph.analyzer.domain",
        "io.reflectoring.coderadar.graph.query.domain",
        "io.reflectoring.coderadar.graph.useradministration.domain");
  }
}
