package io.reflectoring.coderadar.core.analyzer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "metric_value")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricValue {

  @EmbeddedId private MetricValueId id;

  private Long value;

  public Commit getCommit() {
    return id.getCommit();
  }

  public File getFile() {
    return id.getFile();
  }

  public String getMetricName() {
    return id.getMetricName();
  }
}
