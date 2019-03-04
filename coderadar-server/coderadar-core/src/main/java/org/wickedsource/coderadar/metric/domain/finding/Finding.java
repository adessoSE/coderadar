package org.wickedsource.coderadar.metric.domain.finding;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueId;

@Entity
@Table(name = "finding")
@Data
public class Finding {

  @EmbeddedId private MetricValueId id;

  @Column(name = "line_start")
  private Integer lineStart;

  @Column(name = "line_end")
  private Integer lineEnd;

  @Column(name = "char_start")
  private Integer charStart;

  @Column(name = "char_end")
  private Integer charEnd;
}
