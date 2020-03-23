package io.reflectoring.coderadar.graph.analyzer.domain;

import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;

@QueryResult
@Data
public class FileIdAndMetricQueryResult {
  private long id;
  private List<MetricValueEntity> metrics;
}
