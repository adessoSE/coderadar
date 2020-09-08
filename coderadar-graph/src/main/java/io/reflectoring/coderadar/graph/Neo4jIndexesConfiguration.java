package io.reflectoring.coderadar.graph;

import java.util.*;
import javax.annotation.PostConstruct;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Neo4jIndexesConfiguration {

  @Autowired private SessionFactory sessionFactory;

  private static final String LABEL = "label";
  private static final String PROPERTY = "property";

  @PostConstruct
  public void createIndexesAndConstraints() {
    Session session = sessionFactory.openSession();

    // Since Neo4j 4.0 we must first check if indices/constraints already exist.
    List<Map<String, Object>> indicesAndConstaints = new ArrayList<>();

    Result result =
        session.query(
            "CALL db.indexes() YIELD labelsOrTypes, properties WITH head(labelsOrTypes) as label, head(properties) as property RETURN label, property",
            Collections.emptyMap());
    result.queryResults().forEach(indicesAndConstaints::add);
    if (indicesAndConstaints.stream()
        .noneMatch(
            stringObjectMap ->
                stringObjectMap.get(LABEL).equals("ProjectEntity")
                    && stringObjectMap.get(PROPERTY).equals("name"))) {
      session.query("CREATE INDEX ON :ProjectEntity(name)", Collections.emptyMap());
    }
    if (indicesAndConstaints.stream()
        .noneMatch(
            stringObjectMap ->
                stringObjectMap.get(LABEL).equals("CommitEntity")
                    && stringObjectMap.get(PROPERTY).equals("name"))) {
      session.query("CREATE INDEX ON :CommitEntity(name)", Collections.emptyMap());
    }
    if (indicesAndConstaints.stream()
        .noneMatch(
            stringObjectMap ->
                stringObjectMap.get(LABEL).equals("BranchEntity")
                    && stringObjectMap.get(PROPERTY).equals("name"))) {
      session.query("CREATE INDEX ON :BranchEntity(name)", Collections.emptyMap());
    }

    if (indicesAndConstaints.stream()
        .noneMatch(
            stringObjectMap ->
                stringObjectMap.get(LABEL).equals("UserEntity")
                    && stringObjectMap.get(PROPERTY).equals("username"))) {
      session.query(
          "CREATE CONSTRAINT ON (u:UserEntity) ASSERT u.username IS UNIQUE",
          Collections.emptyMap());
    }

    if (indicesAndConstaints.stream()
        .noneMatch(
            stringObjectMap ->
                stringObjectMap.get(LABEL).equals("RefreshTokenEntity")
                    && stringObjectMap.get(PROPERTY).equals("token"))) {
      session.query(
          "CREATE CONSTRAINT ON (r:RefreshTokenEntity) ASSERT r.token IS UNIQUE",
          Collections.emptyMap());
    }
  }
}
