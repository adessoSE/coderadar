package org.wickedsource.coderadar.metric.domain.metricvalue;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.file.domain.File;

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
