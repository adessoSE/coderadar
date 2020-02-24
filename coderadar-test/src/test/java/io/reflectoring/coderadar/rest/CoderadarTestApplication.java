package io.reflectoring.coderadar.rest;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.api.exceptions.KernelException;
import org.neo4j.kernel.impl.proc.Procedures;
import org.neo4j.kernel.internal.GraphDatabaseAPI;
import org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.test.TestGraphDatabaseFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@EnableAsync(proxyTargetClass = true)
@EnableTransactionManagement(proxyTargetClass = true)
@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = "io.reflectoring.coderadar")
@EntityScan(basePackages = "io.reflectoring.coderadar")
public class CoderadarTestApplication {

  @Bean
  public AsyncListenableTaskExecutor taskExecutor() {
    return new ConcurrentTaskExecutor(Runnable::run);
  }

  @Bean
  public GraphDatabaseService graphDatabaseService() throws KernelException {
    GraphDatabaseService db =
        new TestGraphDatabaseFactory().newImpermanentDatabaseBuilder().newGraphDatabase();
    registerProcedure(
        db,
        apoc.path.RelationshipSequenceExpander.class,
        apoc.path.PathExplorer.class,
        apoc.cypher.Cypher.class,
        apoc.graph.Graphs.class,
        apoc.path.RelationshipTypeAndDirections.class);
    return db;
  }

  @Bean
  public SessionFactory sessionFactory() throws KernelException {
    EmbeddedDriver driver = new EmbeddedDriver(graphDatabaseService(), null);
    return new SessionFactory(
        driver,
        "io.reflectoring.coderadar.graph.projectadministration.domain",
        "io.reflectoring.coderadar.graph.analyzer.domain",
        "io.reflectoring.coderadar.graph.query.domain",
        "io.reflectoring.coderadar.graph.useradministration.domain");
  }

  public static void registerProcedure(GraphDatabaseService db, Class<?>... procedures)
      throws KernelException {
    Procedures proceduresService =
        ((GraphDatabaseAPI) db).getDependencyResolver().resolveDependency(Procedures.class);
    for (Class<?> procedure : procedures) {
      proceduresService.registerProcedure(procedure, true);
      proceduresService.registerFunction(procedure, true);
    }
  }

  public static void main(String[] args) {
    SpringApplication.run(CoderadarTestApplication.class, args);
  }
}
