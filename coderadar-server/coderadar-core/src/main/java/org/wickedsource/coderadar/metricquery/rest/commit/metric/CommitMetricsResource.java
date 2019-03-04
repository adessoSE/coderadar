package org.wickedsource.coderadar.metricquery.rest.commit.metric;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueDTO;

/** Result of a query for values of selected metrics at the time of a given commit. */
@SuppressWarnings("unchecked")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class CommitMetricsResource extends ResourceSupport {

  private Map<String, Long> metrics;

  @JsonIgnore
  public void addMetricValues(List<MetricValueDTO> metricValuesPerCommit) {
    for (MetricValueDTO metricValue : metricValuesPerCommit) {
      addMetricValue(metricValue.getMetric(), metricValue.getValue());
    }
  }

  public void addMetricValue(String metric, Long value) {
    initMetrics();
    metrics.put(metric, value);
  }

  private void initMetrics() {
    if (metrics == null) {
      metrics = new HashMap<>();
    }
  }

  public void addAbsentMetrics(List<String> metrics) {
    for (String metric : metrics) {
      if (this.metrics == null || this.metrics.get(metric) == null) {
        addMetricValue(metric, 0L);
      }
    }
  }
}
