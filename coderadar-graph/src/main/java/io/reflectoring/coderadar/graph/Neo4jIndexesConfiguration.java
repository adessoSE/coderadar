package io.reflectoring.coderadar.graph;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Configuration
public class Neo4jIndexesConfiguration {

  @Autowired private SessionFactory sessionFactory;

  @PostConstruct
  public void createIndexesAndConstraints() {
    Session session = sessionFactory.openSession();
    session.query("CREATE INDEX ON :ProjectEntity(name)", Collections.emptyMap());
    session.query("CREATE INDEX ON :CommitEntity(name)", Collections.emptyMap());
    session.query("CREATE INDEX ON :BranchEntity(name)", Collections.emptyMap());

    session.query(
        "CREATE CONSTRAINT ON (r:RefreshTokenEntity) ASSERT r.token IS UNIQUE",
        Collections.emptyMap());
    session.query(
        "CREATE CONSTRAINT ON (u:UserEntity) ASSERT u.username IS UNIQUE", Collections.emptyMap());
  }
}
