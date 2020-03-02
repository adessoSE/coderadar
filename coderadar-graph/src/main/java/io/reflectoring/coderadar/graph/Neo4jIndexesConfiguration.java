package io.reflectoring.coderadar.graph;

import java.util.Collections;
import javax.annotation.PostConstruct;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"prod"}) // Do not use this in tests
public class Neo4jIndexesConfiguration {

  @Autowired private SessionFactory sessionFactory;

  @PostConstruct
  public void createIndexesAndConstraints() {
    Session session = sessionFactory.openSession();
    session.query("CREATE INDEX ON :ProjectEntity(id)", Collections.emptyMap());
    session.query("CREATE INDEX ON :ModuleEntity(id)", Collections.emptyMap());
    session.query("CREATE INDEX ON :CommitEntity(name)", Collections.emptyMap());
    session.query("CREATE INDEX ON :BranchEntity(name)", Collections.emptyMap());

    session.query(
        "CREATE CONSTRAINT ON (r:RefreshTokenEntity) ASSERT r.token IS UNIQUE",
        Collections.emptyMap());
    session.query(
        "CREATE CONSTRAINT ON (p:ProjectEntity) ASSERT p.name IS UNIQUE", Collections.emptyMap());
    session.query(
        "CREATE CONSTRAINT ON (u:UserEntity) ASSERT u.username IS UNIQUE", Collections.emptyMap());
  }
}