package io.reflectoring.coderadar.graph.analyzer.domain;

import java.util.List;
import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
@Data
public class FileIdAndMetricQueryResult {
  private long id;
  private List<MetricValueEntity> metrics;
}
