package org.wickedsource.coderadar.metric.domain.finding;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueId;

@Entity
@Table
public class Finding {

  @EmbeddedId private MetricValueId id;

  @Column private Integer lineStart;

  @Column private Integer lineEnd;

  @Column private Integer charStart;

  @Column private Integer charEnd;

  public MetricValueId getId() {
    return id;
  }

  public void setId(MetricValueId id) {
    this.id = id;
  }

  public Integer getLineStart() {
    return lineStart;
  }

  public void setLineStart(Integer lineStart) {
    this.lineStart = lineStart;
  }

  public Integer getLineEnd() {
    return lineEnd;
  }

  public void setLineEnd(Integer lineEnd) {
    this.lineEnd = lineEnd;
  }

  public Integer getCharStart() {
    return charStart;
  }

  public void setCharStart(Integer charStart) {
    this.charStart = charStart;
  }

  public Integer getCharEnd() {
    return charEnd;
  }

  public void setCharEnd(Integer charEnd) {
    this.charEnd = charEnd;
  }
}
