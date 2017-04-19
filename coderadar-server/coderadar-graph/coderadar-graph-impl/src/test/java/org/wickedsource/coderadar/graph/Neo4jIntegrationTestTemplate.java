package org.wickedsource.coderadar.graph;

import java.util.HashMap;
import org.junit.After;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public abstract class Neo4jIntegrationTestTemplate {

  @Autowired private Session session;

  @SpringBootApplication
  static class TestApplication {}

  @After
  public void resetNeo4JDatabase() {
    session.query("MATCH (n) DETACH DELETE n", new HashMap<>());
  }
}
