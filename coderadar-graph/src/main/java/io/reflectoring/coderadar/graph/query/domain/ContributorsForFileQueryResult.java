package io.reflectoring.coderadar.graph.query.domain;

import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.Set;

@QueryResult
@Data
public class ContributorsForFileQueryResult {
  private String path;
  private Set<String> contributors;
}
