package io.reflectoring.coderadar.graph;

import java.util.Collections;
import javax.annotation.PostConstruct;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Neo4jIndexesConfiguration {

  @Autowired private SessionFactory sessionFactory;

  @PostConstruct
  public void createIndexesAndConstraints() {
    Session session = sessionFactory.openSession();
    session.query(
        "CREATE INDEX project_name IF NOT EXISTS FOR (p:ProjectEntity) ON (p.name)",
        Collections.emptyMap());
    session.query(
        "CREATE INDEX commit_hash IF NOT EXISTS FOR (c:CommitEntity) ON (c.hash)",
        Collections.emptyMap());
    session.query(
        "CREATE INDEX branch_name IF NOT EXISTS FOR (b:BranchEntity) ON (b.name)",
        Collections.emptyMap());

    session.query(
        "CREATE CONSTRAINT refresh_token IF NOT EXISTS ON (r:RefreshTokenEntity) ASSERT r.token IS UNIQUE",
        Collections.emptyMap());
    session.query(
        "CREATE CONSTRAINT username IF NOT EXISTS ON (u:UserEntity) ASSERT u.username IS UNIQUE",
        Collections.emptyMap());
    session.query(
        "CREATE CONSTRAINT team_name IF NOT EXISTS ON (t:TeamEntity) ASSERT t.name IS UNIQUE",
        Collections.emptyMap());
  }
}
