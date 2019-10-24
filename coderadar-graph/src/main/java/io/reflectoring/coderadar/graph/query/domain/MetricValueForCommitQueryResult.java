package io.reflectoring.coderadar.graph.query.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.annotation.QueryResult;

@Data
@NoArgsConstructor
@AllArgsConstructor
@QueryResult
@EqualsAndHashCode
public class MetricValueForCommitQueryResult {
  private String path;
  private String name;
  private Long value;
}
