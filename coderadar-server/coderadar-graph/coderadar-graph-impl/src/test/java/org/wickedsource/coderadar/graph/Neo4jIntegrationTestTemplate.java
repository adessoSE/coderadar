package org.wickedsource.coderadar.graph;

import java.util.HashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public abstract class Neo4jIntegrationTestTemplate {

  @Autowired private Session session;

  @SpringBootApplication
  static class TestApplication {}

  @AfterEach
  public void resetNeo4JDatabase() {
    session.query("MATCH (n) DETACH DELETE n", new HashMap<>());
  }
}
