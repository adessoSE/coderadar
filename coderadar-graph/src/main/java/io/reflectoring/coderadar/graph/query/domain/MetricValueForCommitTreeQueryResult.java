package io.reflectoring.coderadar.graph.query.domain;

import lombok.Getter;
import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
@Getter
public class MetricValueForCommitTreeQueryResult {
  private String path;
  private String[] metrics;
}
