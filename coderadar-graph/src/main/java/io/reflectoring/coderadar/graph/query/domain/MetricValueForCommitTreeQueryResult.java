package io.reflectoring.coderadar.graph.query.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class MetricValueForCommitTreeQueryResult {
  private String path;
  private List<String> metrics;
}
