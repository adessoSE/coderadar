package io.reflectoring.coderadar.graph.query.domain;

import java.util.Set;
import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
@Data
public class ContributorsForFileQueryResult {
  private String path;
  private Set<String> contributors;
}
